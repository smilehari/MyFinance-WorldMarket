package com.harishri.financepal.di

import com.google.firebase.auth.FirebaseAuth
import com.harishri.financepal.data.local.datasource.LocalUserDataSource
import com.harishri.financepal.data.local.dao.UserDao
import com.harishri.financepal.data.local.datasource.WatchlistLocalDataSource
import com.harishri.financepal.data.remote.api.AlphaVantageApiService
import com.harishri.financepal.data.remote.api.MyFinanceApiService
import com.harishri.financepal.data.remote.datasource.RemoteUserDataSource
import com.harishri.financepal.data.remote.datasource.WatchlistRemoteDataSource
import com.harishri.financepal.data.repositories.AuthRepositoryImpl
import com.harishri.financepal.data.repositories.NetworkRepositoryImpl
import com.harishri.financepal.data.repositories.UserRepositoryImpl
import com.harishri.financepal.data.repositories.WatchlistRepositoryImpl
import com.harishri.financepal.domain.repositories.AuthRepository
import com.harishri.financepal.domain.repositories.NetworkRepository
import com.harishri.financepal.domain.repositories.UserRepository
import com.harishri.financepal.domain.repositories.WatchlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(remoteDataSource: RemoteUserDataSource):NetworkRepository{
        return NetworkRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(localUserDataSource : LocalUserDataSource): UserRepository {
        return UserRepositoryImpl(localUserDataSource)
    }

    @Provides
    @Singleton
    fun provideWatchlistRemoteDataSource(
        apiService: AlphaVantageApiService,
        @AlphaVantageApiKey  apiKey: String
    ): WatchlistRemoteDataSource {
        return WatchlistRemoteDataSource(apiService, apiKey)
    }

    @Provides
    @Singleton
    fun provideWatchlistRepository(
        remoteDataSource: WatchlistRemoteDataSource,
        localDataSource: WatchlistLocalDataSource
    ): WatchlistRepository {
        return WatchlistRepositoryImpl(remoteDataSource, localDataSource)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        userDao: UserDao,
        userRepository: UserRepository,
        apiService: MyFinanceApiService,
        localUserDataSource: LocalUserDataSource,
        networkRepository: NetworkRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth,
            userDao,
            userRepository
        )
    }
}