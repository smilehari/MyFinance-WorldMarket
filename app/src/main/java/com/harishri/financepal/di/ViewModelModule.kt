package com.harishri.financepal.di

import com.harishri.financepal.domain.repositories.UserRepository
import com.harishri.financepal.domain.usecase.watchlist.AddStockToWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.GetWatchlistStocksUseCase
import com.harishri.financepal.domain.usecase.watchlist.RemoveStockFromWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.SearchStocksUseCase
import com.harishri.financepal.presentation.viewmodels.WatchlistViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideWatchlistViewModel(
        getWatchlistStocksUseCase: GetWatchlistStocksUseCase,
        addStockToWatchlistUseCase: AddStockToWatchlistUseCase,
        searchStocksUseCase: SearchStocksUseCase,
        removeStockFromWatchlistUseCase: RemoveStockFromWatchlistUseCase,
        userRepository: UserRepository
    ): WatchlistViewModel {
        return WatchlistViewModel(
            getWatchlistStocksUseCase = getWatchlistStocksUseCase,
            addStockToWatchlistUseCase = addStockToWatchlistUseCase,
            searchStocksUseCase = searchStocksUseCase,
            removeStockFromWatchlistUseCase = removeStockFromWatchlistUseCase,
            userRepository = userRepository
        )
    }
}