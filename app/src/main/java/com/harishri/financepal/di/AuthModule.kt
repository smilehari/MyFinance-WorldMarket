package com.harishri.financepal.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Use Hilt to provide the repository instance
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    /*@Provides
    @Singleton
    fun provideGson(): Gson = Gson()*/

    /*@Provides
    @Singleton
    fun provideGoogleSignInOptions(@ApplicationContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
    }*/

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


        /*@Provides
        @Singleton
        fun provideAuthRepository(
            firebaseAuth: FirebaseAuth,
            userDao: UserDao,
            userRepository: UserRepository,
            apiService: MyFinanceApiService,
            localUserDataSource: LocalUserDataSource,
            networkRepository: NetworkRepository
        ): AuthRepository {
            return AuthRepositoryImpl(firebaseAuth,
                userDao,
                userRepository,
                apiService,
                localUserDataSource,
                networkRepository)
        }*/






}




