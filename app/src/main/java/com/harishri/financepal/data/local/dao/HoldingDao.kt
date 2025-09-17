package com.harishri.financepal.data.local.dao

//import com.learn.financepal.data.local.entity.HoldingEntity
/*
@Dao
interface HoldingDao {
    /**
     * Inserts a list of holdings. If a holding already exists, it will be replaced.
     * This is useful for syncing data from a remote source.
     * @param holdings The list of holdings to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldings(holdings: List<HoldingEntity>) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(holding: HoldingEntity):Int

    @Update
    suspend fun updateHolding(holding: HoldingEntity):Int

    @Delete
    suspend fun deleteHolding(holding: HoldingEntity):Int

    @Query("DELETE FROM holdings WHERE userId = :userId")
    suspend fun deleteAllHoldingsForUser(userId: String):Long

    @Query("DELETE FROM holdings WHERE userId = :userId AND symbol = :symbol")
    suspend fun deleteHoldingBySymbol(userId: String, symbol: String):Int

    // Portfolio Calculations
    /*@Query("""
        SELECT SUM(quantity * currentPrice) as totalValue 
        FROM holdings 
        WHERE userId = :userId AND quantity > 0
    """)
    suspend fun getTotalPortfolioValue(userId: String): Double?

    @Query("""
        SELECT SUM(quantity * averagePrice) as totalInvestment 
        FROM holdings 
        WHERE userId = :userId AND quantity > 0
    """)
    suspend fun getTotalInvestment(userId: String): Double?

    @Query("""
        SELECT SUM((currentPrice - averagePrice) * quantity) as totalPnL 
        FROM holdings 
        WHERE userId = :userId AND quantity > 0
    """)
    suspend fun getTotalUnrealizedPnL(userId: String): Double?

    @Query("""
        SELECT SUM((currentPrice - averagePrice) * quantity) as dayChange
        FROM holdings 
        WHERE userId = :userId AND quantity > 0 
        AND lastPriceUpdate > :startOfDay
    """)
    suspend fun getDayChange(userId: String, startOfDay: Long): Double?

    // Top Gainers and Losers
    @Query("""
        SELECT *, ((currentPrice - averagePrice) * quantity) as pnl,
               ((currentPrice - averagePrice) / averagePrice * 100) as pnlPercent
        FROM holdings 
        WHERE userId = :userId AND quantity > 0
        ORDER BY pnl DESC 
        LIMIT :limit
    """)
    suspend fun getTopGainers(userId: String, limit: Int = 5): List<HoldingEntity>

    @Query("""
        SELECT *, ((currentPrice - averagePrice) * quantity) as pnl,
               ((currentPrice - averagePrice) / averagePrice * 100) as pnlPercent
        FROM holdings 
        WHERE userId = :userId AND quantity > 0
        ORDER BY pnl ASC 
        LIMIT :limit
    """)
    suspend fun getTopLosers(userId: String, limit: Int = 5): List<HoldingEntity>

    // Portfolio Statistics
    @Query("SELECT COUNT(*) FROM holdings WHERE userId = :userId AND quantity > 0")
    suspend fun getActiveHoldingsCount(userId: String): Int

    @Query("""
        SELECT symbol FROM holdings 
        WHERE userId = :userId AND quantity > 0 
        ORDER BY (quantity * currentPrice) DESC
    """)
    suspend fun getHoldingSymbolsByValue(userId: String): List<String>

    // Price Updates
    @Query("UPDATE holdings SET currentPrice = :price, lastPriceUpdate = :timestamp WHERE symbol = :symbol")
    suspend fun updateCurrentPrice(symbol: String, price: Double, timestamp: Long = System.currentTimeMillis())

    @Query("""
        UPDATE holdings 
        SET currentPrice = :price, lastPriceUpdate = :timestamp 
        WHERE userId = :userId AND symbol = :symbol
    """)
    suspend fun updateCurrentPriceForUser(userId: String, symbol: String, price: Double, timestamp: Long = System.currentTimeMillis())

    // Search and Filter
    @Query("""
        SELECT * FROM holdings 
        WHERE userId = :userId AND quantity > 0
        AND (symbol LIKE '%' || :searchQuery || '%' OR companyName LIKE '%' || :searchQuery || '%')
        ORDER BY updatedAt DESC
    """)
    suspend fun searchHoldings(userId: String, searchQuery: String): List<HoldingEntity>*/


    /**
     * Gets a Flow of all holdings for a specific user.
     * This is crucial for the UI to automatically update when a holding changes.
     * @param userId The ID of the user to get holdings for.
     * @return A Flow of a list of HoldingEntity objects.
     */
    @Query("SELECT * FROM holdings WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getHoldingsForUser(userId: String): Flow<List<HoldingEntity>>

    @Query("SELECT * FROM holdings WHERE userId = :userId AND symbol = :symbol")
    suspend fun getHoldingBySymbol(userId: String, symbol: String): HoldingEntity?
}*/