package com.harishri.financepal.di

import com.google.android.datatransport.BuildConfig
import com.harishri.financepal.data.remote.api.MyFinanceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://www.alphavantage.co/"
    private const val ANGELONE_BASE_URL = "https://apiconnect.angelone.in"
    private const val ANGELONE_AUTH_KEY ="e34cbfbd-bb3b-46e5-872b-f695bf21ccb2"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MyFinanceRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MyFinanceOkhttpClient


    // Rate limiting interceptor (Alpha Vantage allows 5 requests per minute)
    private val rateLimitInterceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        // Add delay to respect rate limits
        Thread.sleep(12000) // 12 seconds between requests (5 per minute)
        response
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    // Error response interceptor
    private val errorInterceptor = Interceptor { chain ->
        val request = chain.request()
        val response: okhttp3.Response = chain.proceed(request)

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        response
    }

    /**
     * Provides a singleton instance of OkHttpClient.
     * Includes a logging interceptor for debugging network requests.*/

    @Provides
    @Singleton
    @MyFinanceOkhttpClient
    fun provideOkHttpClient(): OkHttpClient {
        val rateLimitInterceptor = Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            // Add delay to respect rate limits
            Thread.sleep(12000) // 12 seconds between requests (5 per minute)

            response
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val errorInterceptor = Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            if (!response.isSuccessful) {
                throw HttpException(response)
            }
            response
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(rateLimitInterceptor)
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit.
     * This is the core networking client for the app.
     */
    @Provides
    @Singleton
    @MyFinanceRetrofit
    fun provideMyFinanceRetrofitRetrofit(@MyFinanceOkhttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides a singleton instance of the StockMarketApi service.
     * This uses the Retrofit instance to create the API service.
    @Provides
    @Singleton
    fun provideStockMarketApi(retrofit: Retrofit): AlphaVantageApiService {
        return retrofit.create(AlphaVantageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAlphaVantageApiKey(): String {
        return API_KEY
    }*/


    @Provides
    @Singleton
    fun provideMyFinanceApiService(@MyFinanceRetrofit retrofit: Retrofit): MyFinanceApiService {
        return retrofit.create(MyFinanceApiService::class.java)
    }
}
class HttpException(val response: okhttp3.Response) : Exception("HTTP ${response.code}: ${response.message}")
