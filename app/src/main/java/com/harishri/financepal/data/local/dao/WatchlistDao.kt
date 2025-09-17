package com.harishri.financepal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.harishri.financepal.data.local.entity.WatchlistEntity



@Dao
interface WatchlistDao: BaseDao<WatchlistEntity> {
    // Basic CRUD Operations

    //Multi user support will fail here
    @Query("SELECT * FROM watchlist ORDER BY sortOrder ASC")
    fun getAllWatchlistStocks(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE userId = :userId ORDER BY addedAt DESC")
    fun getWatchlistItems(userId: String): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE userId = :userId AND symbol = :symbol")
    suspend fun getWatchlistItem(userId: String, symbol: String): WatchlistEntity?

    @Query("UPDATE watchlist SET sortOrder = :newOrder WHERE symbol = :symbol")
    suspend fun updateSortOrder(symbol: String, newOrder: Int):Int

    @Query("SELECT MAX(sortOrder) FROM watchlist WHERE userId = :userId")
    suspend fun getMaxSortOrder(userId: String): Int

    @Delete
    suspend fun deleteWatchlistItem(item: WatchlistEntity):Int

    @Query("DELETE FROM watchlist WHERE userId = :userId AND symbol = :symbol")
    suspend fun deleteWatchlistItemBySymbol(userId: String, symbol: String):Int

    @Query("DELETE FROM watchlist WHERE userId = :userId")
    suspend fun deleteAllWatchlistItemsForUser(userId: String)

    // Check if stock exists in watchlist
    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE userId = :userId AND symbol = :symbol)")
    suspend fun isStockInWatchlist(userId:String, symbol: String): Boolean



    // Price Updates
    /*@Query("""
        UPDATE watchlist 
        SET currentPrice = :currentPrice, 
            changePercent = ((currentPrice - previousClose) / previousClose * 100),
            lastPriceUpdate = :timestamp
        WHERE symbol = :symbol
    """)
    suspend fun updateWatchlistPrice(symbol: String, currentPrice: Double, timestamp: Long = System.currentTimeMillis())

    @Query("""
        UPDATE watchlist 
        SET currentPrice = :currentPrice,
            previousClose = :previousClose,
            changePercent = ((:currentPrice - :previousClose) / :previousClose * 100),
            lastPriceUpdate = :timestamp
        WHERE symbol = :symbol
    """)
    suspend fun updateWatchlistPriceData(symbol: String, currentPrice: Double, previousClose: Double, timestamp: Long = System.currentTimeMillis())

    // Alerts and Filtering
    @Query("SELECT * FROM watchlist WHERE userId = :userId AND alertEnabled = 1")
    suspend fun getWatchlistItemsWithAlerts(userId: String): List<WatchlistEntity>

    @Query("""
        SELECT * FROM watchlist 
        WHERE userId = :userId 
        AND alertEnabled = 1 
        AND ((targetPrice IS NOT NULL AND currentPrice >= targetPrice) 
             OR (stopLoss IS NOT NULL AND currentPrice <= stopLoss))
    """)
    suspend fun getTriggeredAlerts(userId: String): List<WatchlistEntity>

    // Top Movers
    @Query("""
        SELECT * FROM watchlist 
        WHERE userId = :userId 
        ORDER BY changePercent DESC 
        LIMIT :limit
    """)
    suspend fun getTopGainers(userId: String, limit: Int = 5): List<WatchlistEntity>

    @Query("""
        SELECT * FROM watchlist 
        WHERE userId = :userId 
        ORDER BY changePercent ASC 
        LIMIT :limit
    """)
    suspend fun getTopLosers(userId: String, limit: Int = 5): List<WatchlistEntity>

    // Search
    @Query("""
        SELECT * FROM watchlist 
        WHERE userId = :userId 
        AND (symbol LIKE '%' || :searchQuery || '%' OR companyName LIKE '%' || :searchQuery || '%')
        ORDER BY addedAt DESC
    """)
    suspend fun searchWatchlistItems(userId: String, searchQuery: String): List<WatchlistEntity>*/

    // Statistics
    @Query("SELECT COUNT(*) FROM watchlist WHERE userId = :userId")
    suspend fun getWatchlistCount(userId: String): Int

    @Query("SELECT COUNT(*) FROM watchlist WHERE userId = :userId AND alertEnabled = 1")
    suspend fun getAlertsEnabledCount(userId: String): Int

    @Query("SELECT symbol FROM watchlist WHERE userId = :userId")
    suspend fun getAllWatchlistSymbols(userId: String): List<String>

    @Query("SELECT COALESCE(MAX(sortOrder), -1) + 1 FROM watchlist  WHERE userId = :userId")
    suspend fun getNextSortOrder(userId: String): Int
}
