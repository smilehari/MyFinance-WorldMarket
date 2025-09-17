package com.harishri.financepal.data.local.datasource

import com.harishri.financepal.data.local.dao.WatchlistDao
import com.harishri.financepal.data.local.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistLocalDataSource @Inject constructor(
    private val watchlistDao: WatchlistDao
) {
    /**
     * Inserts a watchlist item into the local database.
     * @param watchlistItem The `WatchlistEntity` to insert.
     */
    suspend fun insertWatchlistItem(watchlistItem: WatchlistEntity) {
        watchlistDao.insert(watchlistItem)
    }

    /**
     * Retrieves all watchlist items for a specific user as a Flow for real-time updates.
     * @return A Flow of a list of `WatchlistEntity` objects.
     */
    fun getWatchlistItems(userId: String): Flow<List<WatchlistEntity>> {
        return watchlistDao.getWatchlistItems(userId)
    }

    suspend fun isStockInWatchlist(userId: String,symbol: String): Boolean{
        return watchlistDao.isStockInWatchlist(userId= userId, symbol =  symbol)
    }

    /**
     * Deletes a watchlist item from the database.
     * @return The number of rows deleted.
     */
    suspend fun deleteWatchlistItem(userId: String , symbol: String): Int {
        return watchlistDao.deleteWatchlistItemBySymbol(userId,symbol)
    }

    /**
     * Retrieves a single watchlist item by symbol and user ID.
     * @param userId The ID of the user.
     * @param symbol The stock symbol to retrieve.
     * @return The `WatchlistEntity` object, or null if not found.
     */
    suspend fun getWatchlistItem(userId: String, symbol: String): WatchlistEntity? {
        return watchlistDao.getWatchlistItem(userId, symbol)
    }

    suspend fun getNextSortOrder(userId: String): Int {
        return watchlistDao.getNextSortOrder(userId)

    }
}