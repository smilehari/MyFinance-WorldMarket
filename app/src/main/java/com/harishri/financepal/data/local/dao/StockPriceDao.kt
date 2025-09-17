package com.harishri.financepal.data.local.dao

//import com.learn.financepal.data.local.entity.StockPriceEntity

/*@Dao
interface StockPriceDao {
    // Basic CRUD Operations
    @Query("SELECT * FROM stock_prices WHERE symbol = :symbol")
    suspend fun getStockPrice(symbol: String): StockPriceEntity?

    @Query("SELECT * FROM stock_prices WHERE symbol IN (:symbols)")
    suspend fun getStockPrices(symbols: List<String>): List<StockPriceEntity>

    @Query("SELECT * FROM stock_prices ORDER BY lastUpdated DESC")
    suspend fun getAllStockPrices(): Flow<List<StockPriceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockPrice(stockPrice: StockPriceEntity) :Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockPrices(stockPrices: List<StockPriceEntity>) :Long

    @Update
    suspend fun updateStockPrice(stockPrice: StockPriceEntity) :Int

    @Delete
    suspend fun deleteStockPrice(stockPrice: StockPriceEntity) :Int

    @Query("DELETE FROM stock_prices WHERE symbol = :symbol")
    suspend fun deleteStockPriceBySymbol(symbol: String) :Int

    /**
     * Deletes all stock prices from the cache.
     * This is useful for clearing outdated data.
     */
    @Query("DELETE FROM stock_prices")
    suspend fun deleteAllStockPrices() :Long

    // Price Updates
    /*@Query(
        """
        UPDATE stock_prices 
        SET currentPrice = :currentPrice,
            change = :currentPrice - previousClose,
            changePercent = ((:currentPrice - previousClose) / previousClose * 100),
            lastUpdated = :timestamp
        WHERE symbol = :symbol
    """
    )
    suspend fun updateCurrentPrice(symbol: String, currentPrice: Double, timestamp: Long = System.currentTimeMillis())

    @Query(
        """
        UPDATE stock_prices 
        SET currentPrice = :currentPrice,
            previousClose = :previousClose,
            dayHigh = :dayHigh,
            dayLow = :dayLow,
            volume = :volume,
            change = :currentPrice - :previousClose,
            changePercent = ((:currentPrice - :previousClose) / :previousClose * 100),
            lastUpdated = :timestamp
        WHERE symbol = :symbol
    """
    )
    suspend fun updateStockPriceData(
        symbol: String,
        currentPrice: Double,
        previousClose: Double,
        dayHigh: Double,
        dayLow: Double,
        volume: Long,
        timestamp: Long = System.currentTimeMillis()
    )*/

    // Data Freshness
    @Query("SELECT * FROM stock_prices WHERE lastUpdated < :staleTime")
    suspend fun getStaleStockPrices(staleTime: Long): List<StockPriceEntity>

    @Query("SELECT * FROM stock_prices WHERE lastUpdated >= :freshTime ORDER BY lastUpdated DESC")
    suspend fun getFreshStockPrices(freshTime: Long): List<StockPriceEntity>

    @Query("SELECT COUNT(*) FROM stock_prices WHERE lastUpdated < :staleTime")
    suspend fun getStaleStockPricesCount(staleTime: Long): Int

    // Market Analysis
    /*@Query("""
        SELECT * FROM stock_prices 
        WHERE exchange = :exchange 
        ORDER BY changePercent DESC 
        LIMIT :limit
    """)
    suspend fun getTopGainersByExchange(exchange: String, limit: Int = 10): List<StockPriceEntity>

    @Query("""
        SELECT * FROM stock_prices 
        WHERE exchange = :exchange 
        ORDER BY changePercent ASC 
        LIMIT :limit
    """)
    suspend fun getTopLosersByExchange(exchange: String, limit: Int = 10): List<StockPriceEntity>

    @Query("""
        SELECT * FROM stock_prices 
        WHERE exchange = :exchange 
        ORDER BY volume DESC 
        LIMIT :limit
    """)
    suspend fun getMostActiveByExchange(exchange: String, limit: Int = 10): List<StockPriceEntity>

    // Search
    @Query("""
        SELECT * FROM stock_prices 
        WHERE symbol LIKE '%' || :searchQuery || '%' 
           OR companyName LIKE '%' || :searchQuery || '%'
        ORDER BY symbol ASC
    """)
    suspend fun searchStocks(searchQuery: String): List<StockPriceEntity>*/

    // Cache Management
    @Query("DELETE FROM stock_prices WHERE lastUpdated < :cutoffTime")
    suspend fun deleteOldStockPrices(cutoffTime: Long)

    @Query("SELECT COUNT(*) FROM stock_prices")
    suspend fun getStockPricesCount(): Int
}*/