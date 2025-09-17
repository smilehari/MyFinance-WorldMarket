package com.harishri.financepal.data.local.dao

//import com.learn.financepal.data.local.entity.TransactionEntity
/*
@Dao
interface TransactionDao {
    // Basic CRUD Operations
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY transactionDate DESC")
    fun getAllTransactionsByUser(userId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND symbol = :symbol ORDER BY transactionDate DESC")
    suspend fun getTransactionsBySymbol(userId: String, symbol: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: String): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity) :Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>) :Long

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity) :Int

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity) :Int

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: String) :Int

    @Query("DELETE FROM transactions WHERE userId = :userId")
    suspend fun deleteAllTransactionsForUser(userId: String) :Long

    // Transaction Analysis
    /*@Query("""
        SELECT * FROM transactions 
        WHERE userId = :userId 
        AND transactionDate BETWEEN :startDate AND :endDate
        ORDER BY transactionDate DESC
    """)
    suspend fun getTransactionsByDateRange(userId: String, startDate: Long, endDate: Long): List<TransactionEntity>

    @Query("""
        SELECT * FROM transactions 
        WHERE userId = :userId AND transactionType = :type
        ORDER BY transactionDate DESC
    """)
    suspend fun getTransactionsByType(userId: String, type: TransactionType): List<TransactionEntity>*/

    // Portfolio Calculations
   /* @Query("""
        SELECT SUM(CASE 
            WHEN transactionType = 'BUY' THEN quantity 
            ELSE -quantity 
        END) as netQuantity
        FROM transactions 
        WHERE userId = :userId AND symbol = :symbol
    """)
    suspend fun getNetQuantityForSymbol(userId: String, symbol: String): Double?

    @Query("""
        SELECT 
            SUM(CASE WHEN transactionType = 'BUY' THEN totalAmount ELSE 0 END) as totalBought,
            SUM(CASE WHEN transactionType = 'SELL' THEN totalAmount ELSE 0 END) as totalSold,
            SUM(CASE WHEN transactionType = 'BUY' THEN quantity ELSE -quantity END) as netQuantity
        FROM transactions 
        WHERE userId = :userId AND symbol = :symbol
    """)
    suspend fun getTransactionSummaryForSymbol(userId: String, symbol: String): TransactionSummary?*/

    // Realized P&L Calculation
    /*@Query("""
        SELECT SUM(
            CASE WHEN transactionType = 'SELL' 
            THEN totalAmount - (quantity * :averagePrice)
            ELSE 0 
            END
        ) as realizedPnL
        FROM transactions 
        WHERE userId = :userId AND symbol = :symbol
    """)
    suspend fun getRealizedPnLForSymbol(userId: String, symbol: String, averagePrice: Double): Double?

    @Query("""
        SELECT SUM(totalAmount) as totalInvested
        FROM transactions 
        WHERE userId = :userId AND transactionType = 'BUY'
    """)
    suspend fun getTotalAmountInvested(userId: String): Double?

    @Query("""
        SELECT SUM(totalAmount) as totalRealized  
        FROM transactions 
        WHERE userId = :userId AND transactionType = 'SELL'
    """)
    suspend fun getTotalAmountRealized(userId: String): Double?*/

    // Statistics
    /*@Query("SELECT COUNT(*) FROM transactions WHERE userId = :userId")
    suspend fun getTransactionCount(userId: String): Int

    @Query("""
        SELECT symbol, COUNT(*) as count 
        FROM transactions 
        WHERE userId = :userId 
        GROUP BY symbol 
        ORDER BY count DESC 
        LIMIT :limit
    """)
    suspend fun getMostTradedSymbols(userId: String, limit: Int = 10): List<SymbolCount>

    // Recent Transactions
    @Query("""
        SELECT * FROM transactions 
        WHERE userId = :userId 
        ORDER BY createdAt DESC 
        LIMIT :limit
    """)
    suspend fun getRecentTransactions(userId: String, limit: Int = 10): List<TransactionEntity>*/
}

// Data classes for complex queries
data class TransactionSummary(
    val totalBought: Double,
    val totalSold: Double,
    val netQuantity: Double
)

data class SymbolCount(
    val symbol: String,
    val count: Int
)*/