package com.harishri.financepal.data.local.dao

//import com.learn.financepal.data.local.entity.PriceAlertEntity

/*Smart alert triggering with automatic price comparison
Multi-type alerts: Target, Stop-loss, Percentage change
Bulk alert management and cleanup functions
Alert statistics and trending analysis*/
/*@Dao
interface PriceAlertDao {
    // Basic CRUD Operations
    @Query("SELECT * FROM price_alerts WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllAlertsByUser(userId: String): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE id = :alertId")
    suspend fun getAlertById(alertId: String): PriceAlertEntity?

    @Query("SELECT * FROM price_alerts WHERE userId = :userId AND symbol = :symbol")
    suspend fun getAlertsBySymbol(userId: String, symbol: String): List<PriceAlertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: PriceAlertEntity) :Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<PriceAlertEntity>) :Long

    @Update
    suspend fun updateAlert(alert: PriceAlertEntity) :Int

    @Delete
    suspend fun deleteAlert(alert: PriceAlertEntity) :Int

    @Query("DELETE FROM price_alerts WHERE id = :alertId")
    suspend fun deleteAlertById(alertId: String) :Int

    @Query("DELETE FROM price_alerts WHERE userId = :userId")
    suspend fun deleteAllAlertsForUser(userId: String) :Long

    // Alert Management

    /**
     * Retrieves all active price alerts for a specific user as a Flow.
     * @param userId The ID of the user.
     * @return A Flow of a list of `PriceAlertEntity` objects that are currently active.
     */
    @Query("SELECT * FROM price_alerts WHERE userId = :userId AND isActive = 1 ORDER BY createdAt DESC")
    suspend fun getActiveAlerts(userId: String):  Flow<List<PriceAlertEntity>>

    /**
     * Retrieves all triggered (but not necessarily inactive) price alerts for a specific user as a Flow.
     * @param userId The ID of the user.
     * @return A Flow of a list of `PriceAlertEntity` objects that have been triggered.
     */
    @Query("SELECT * FROM price_alerts WHERE userId = :userId AND isTriggered = 1 ORDER BY triggeredAt DESC")
    suspend fun getTriggeredAlerts(userId: String):  Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE isActive = 1 AND isTriggered = 0")
    suspend fun getAllActiveUntriggeredAlerts():  Flow<List<PriceAlertEntity>>

    // Alert Triggering
    /*@Query("""
        SELECT * FROM price_alerts 
        WHERE isActive = 1 AND isTriggered = 0 
        AND symbol = :symbol
        AND ((alertType = 'TARGET' AND :currentPrice >= targetPrice) 
             OR (alertType = 'STOP_LOSS' AND :currentPrice <= targetPrice))
    """)
    suspend fun getTriggeredAlertsForSymbol(symbol: String, currentPrice: Double): List<PriceAlertEntity>

    @Query("""
        UPDATE price_alerts 
        SET isTriggered = 1, triggeredAt = :timestamp 
        WHERE id = :alertId
    """)
    suspend fun markAlertAsTriggered(alertId: String, timestamp: Long = System.currentTimeMillis())

    @Query("""
        UPDATE price_alerts 
        SET isActive = :isActive 
        WHERE id = :alertId
    """)
    suspend fun updateAlertActiveStatus(alertId: String, isActive: Boolean)

    // Alert Types
    @Query("""
        SELECT * FROM price_alerts 
        WHERE userId = :userId AND alertType = :alertType AND isActive = 1
        ORDER BY createdAt DESC
    """)
    suspend fun getAlertsByType(userId: String, alertType: AlertType): List<PriceAlertEntity>*/

    // Statistics
    @Query("SELECT COUNT(*) FROM price_alerts WHERE userId = :userId")
    suspend fun getAlertCount(userId: String): Int

    @Query("SELECT COUNT(*) FROM price_alerts WHERE userId = :userId AND isActive = 1")
    suspend fun getActiveAlertCount(userId: String): Int

    @Query("SELECT COUNT(*) FROM price_alerts WHERE userId = :userId AND isTriggered = 1")
    suspend fun getTriggeredAlertCount(userId: String): Int

    /*@Query("""
        SELECT symbol, COUNT(*) as count 
        FROM price_alerts 
        WHERE userId = :userId 
        GROUP BY symbol 
        ORDER BY count DESC 
        LIMIT :limit
    """)
    suspend fun getMostAlertedSymbols(userId: String, limit: Int = 10): List<SymbolCount>*/

    // Cleanup
    @Query("DELETE FROM price_alerts WHERE isTriggered = 1 AND triggeredAt < :cutoffTime")
    suspend fun deleteOldTriggeredAlerts(cutoffTime: Long)

    @Query("DELETE FROM price_alerts WHERE isActive = 0 AND createdAt < :cutoffTime")
    suspend fun deleteOldInactiveAlerts(cutoffTime: Long)
}*/