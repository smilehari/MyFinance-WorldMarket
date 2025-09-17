package com.harishri.financepal.domain.usecase.watchlist

import com.harishri.financepal.domain.repositories.WatchlistRepository
import com.harishri.financepal.data.remote.utils.Result
import javax.inject.Inject

class AddStockToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    /**
     * Executes the operation to add a stock to the watchlist.
     * @param symbol The stock symbol to add.
     * @param userId The ID of the user.
     */
    suspend operator fun invoke(symbol: String, userId: String,name: String): Result<Unit> {
        return watchlistRepository.addStockToWatchlist(symbol, userId,name)
    }
}