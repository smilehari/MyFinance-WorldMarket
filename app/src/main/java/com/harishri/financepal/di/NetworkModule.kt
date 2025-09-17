package com.harishri.financepal.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.harishri.financepal.BuildConfig
import com.harishri.financepal.data.local.device.DeviceSecurityChecker
import com.harishri.financepal.data.local.preference.PreferencesManager
import com.harishri.financepal.data.remote.api.AlphaVantageApiService
import com.harishri.financepal.data.remote.api.MyFinanceApiService
import com.harishri.financepal.data.remote.NetworkMonitor
import com.harishri.financepal.data.remote.datasource.RemoteUserDataSource
import com.harishri.financepal.data.remote.interceptors.AlphaVantageErrorInterceptor
import com.harishri.financepal.data.remote.interceptors.AuthInterceptor
import com.harishri.financepal.data.remote.interceptors.CacheInterceptor
import com.harishri.financepal.data.remote.interceptors.ConnectivityInterceptor
import com.harishri.financepal.data.remote.interceptors.ErrorHandlingInterceptor
import com.harishri.financepal.data.remote.interceptors.RateLimitInterceptor
import com.harishri.financepal.data.remote.interceptors.SecurityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/*@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class YahooFinanceRetrofit*/

//@Qualifier tells Hilt/Dagger: "This is a custom identifier" - Creates a unique "tag" for dependency injection
//Without it: Hilt/Dagger can't distinguish between different Retrofit instances YahooFinanceRetrofit and AlphaVantageRetrofit because both returns a Retrofit object
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AlphaVantageRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AlphaVantageOkHttpClinet

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AlphaVantageApiKey

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //private const val YAHOO_FINANCE_BASE_URL = "https://query1.finance.yahoo.com/"
    private const val ALPHA_VANTAGE_BASE_URL = "https://www.alphavantage.co/"

    const val API_KEY = ""


    // Network timeouts
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L

    // Cache settings
    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
    const val CACHE_MAX_AGE = 60 * 5 // 5 minutes for fresh data
    const val CACHE_MAX_STALE = 60 * 60 * 24 * 7 // 7 days for offline

    @Provides
    @AlphaVantageApiKey
    fun provideAlphaVantageApiKey(): String {
        // In production, get this from BuildConfig or encrypted preferences
        return API_KEY // Add this to your build.gradle
    }
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient() // Alpha Vantage sometimes returns non-standard JSON
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }


    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            //TODO
            // Alpha Vantage SSL pins
            .add("www.alphavantage.co", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        appPreferences: PreferencesManager
    ): AuthInterceptor {
        return AuthInterceptor(appPreferences)
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(
        networkMonitor: NetworkMonitor
    ): CacheInterceptor {
        return CacheInterceptor(networkMonitor)
    }

    @Provides
    @Singleton
    fun provideRateLimitInterceptor(): RateLimitInterceptor {
        return RateLimitInterceptor()
    }

    @Provides
    @Singleton
    fun provideSecurityInterceptor(
        deviceSecurityChecker: DeviceSecurityChecker
    ): SecurityInterceptor {
        return SecurityInterceptor(deviceSecurityChecker)
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(): ConnectivityInterceptor {
        return ConnectivityInterceptor()
    }

    @Provides
    @Singleton
    fun provideAlphaVantageErrorInterceptor(): AlphaVantageErrorInterceptor {
        return AlphaVantageErrorInterceptor()
    }

    @Provides
    @Singleton
    fun provideErrorHandlingInterceptor(): ErrorHandlingInterceptor {
        return ErrorHandlingInterceptor()
    }

    @Provides
    @Singleton
    @AlphaVantageOkHttpClinet
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner,
        authInterceptor: AuthInterceptor,
        cacheInterceptor: CacheInterceptor,
        rateLimitInterceptor: RateLimitInterceptor,
        securityInterceptor: SecurityInterceptor,
        errorHandlingInterceptor: ErrorHandlingInterceptor,
        errorInterceptor: AlphaVantageErrorInterceptor,
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // Timeouts
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

            // Cache
            .cache(cache)

            // Security
            //.certificatePinner(certificatePinner)

            // Interceptors (order matters!)
            //.addInterceptor(securityInterceptor) // First - check device security
            .addInterceptor(authInterceptor) // Add auth headers
            .addInterceptor(rateLimitInterceptor) // Rate limiting
            //.addInterceptor(cacheInterceptor) // Cache control
            //.addInterceptor(errorHandlingInterceptor) // Error handling
            .addNetworkInterceptor(loggingInterceptor) // Logging (last)
            // Connection settings
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            //.followSslRedirects(false)
            .build()
    }

    /*@Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            // TODO: Add SSL pinning logic here in the future for enhanced security.
            .build()
    }*/

//    @Provides
//    @Singleton
//    @YahooFinanceRetrofit
//    fun provideYahooFinanceRetrofit(
//        okHttpClient: OkHttpClient,
//        gson: Gson
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(YAHOO_FINANCE_BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

    @Provides
    @Singleton
    @AlphaVantageRetrofit
    fun provideAlphaVantageRetrofit(
        @AlphaVantageOkHttpClinet okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(ALPHA_VANTAGE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    /*@Provides
    @Singleton
    fun provideYahooFinanceApiService(
        @YahooFinanceRetrofit retrofit: Retrofit
    ): YahooFinanceApiService {
        return retrofit.create(YahooFinanceApiService::class.java)
    }*/

    @Provides
    @Singleton
    fun provideAlphaVantageApiService(
        @AlphaVantageRetrofit retrofit: Retrofit
    ): AlphaVantageApiService {
        return retrofit.create(AlphaVantageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteUserDataSource(apiService: MyFinanceApiService):RemoteUserDataSource{
        return RemoteUserDataSource(apiService)
    }


}









