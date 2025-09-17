package com.harishri.financepal.domain.usecase.watchlist

import com.harishri.financepal.data.remote.utils.Result
import com.harishri.financepal.domain.repositories.WatchlistRepository
import javax.inject.Inject

class RemoveStockFromWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    suspend operator fun invoke(userId: String, symbol: String): Result<Unit> {
        return repository.removeFromWatchlist(userId, symbol)
    }
}