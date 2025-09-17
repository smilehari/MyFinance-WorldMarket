package com.harishri.financepal.di

import com.harishri.financepal.domain.repositories.WatchlistRepository
import com.harishri.financepal.domain.usecase.watchlist.AddStockToWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.GetWatchlistStocksUseCase
import com.harishri.financepal.domain.usecase.watchlist.RemoveStockFromWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.SearchStocksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetWatchlistStocksUseCase(repository: WatchlistRepository): GetWatchlistStocksUseCase {
        return GetWatchlistStocksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchStocksUseCase(repository: WatchlistRepository): SearchStocksUseCase {
        return SearchStocksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddStockToWatchlistUseCase(repository: WatchlistRepository): AddStockToWatchlistUseCase {
        return AddStockToWatchlistUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveStockFromWatchlistUseCase(repository: WatchlistRepository): RemoveStockFromWatchlistUseCase{
        return RemoveStockFromWatchlistUseCase(repository)

    }
}