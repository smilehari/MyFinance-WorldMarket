package com.harishri.financepal.domain.usecase.watchlist

import com.harishri.financepal.data.local.entity.WatchlistEntity
import com.harishri.financepal.domain.repositories.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetWatchlistStocksUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    /**
     * Retrieves all stocks from the local watchlist for a specific user.
     * @param userId The ID of the user.
     * @return A Flow of a list of `WatchlistEntity` objects.
     */
    operator fun invoke(userId: String): Flow<List<WatchlistEntity>> {
        return watchlistRepository.getWatchlistStocks(userId)
    }
}