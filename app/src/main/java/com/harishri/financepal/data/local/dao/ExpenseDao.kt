package com.harishri.financepal.data.local.dao

//import com.learn.financepal.data.local.entity.ExpenseEntity

/* Personal Finance
Category analysis with monthly summaries - Recurring expense tracking
Date range filtering and totals - Spending statistics and trends */
/*@Dao
interface ExpenseDao {

    // Basic CRUD Operations
    @Insert
    suspend fun insertExpense(expense: ExpenseEntity):Int

    @Insert
    suspend fun insertExpenses(expenses: List<ExpenseEntity>): Long

    @Update
    suspend fun updateExpense(expense: ExpenseEntity):Int

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity):Int

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: String) :Int

    @Query("DELETE FROM expenses WHERE userId = :userId")
    suspend fun deleteAllExpensesForUser(userId: String) :Long

    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY expenseDate DESC")
    fun getAllExpensesByUser(userId: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: String): ExpenseEntity?



    // Date Range Queries
    @Query(" SELECT * FROM expenses WHERE userId = :userId AND expenseDate BETWEEN :startDate AND :endDate ORDER BY expenseDate DESC")
    suspend fun getExpensesByDateRange(userId: String, startDate: Long, endDate: Long): List<ExpenseEntity>

    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startOfMonth AND :endOfMonth
        ORDER BY expenseDate DESC
    """)
    suspend fun getMonthlyExpenses(userId: String, startOfMonth: Long, endOfMonth: Long): List<ExpenseEntity>

    // Category Analysis
    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId AND category = :category
        ORDER BY expenseDate DESC
    """)
    suspend fun getExpensesByCategory(userId: String, category: ExpenseCategory): List<ExpenseEntity>

    @Query("""
        SELECT category, SUM(amount) as total
        FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startDate AND :endDate
        GROUP BY category
        ORDER BY total DESC
    """)
    suspend fun getCategoryTotals(userId: String, startDate: Long, endDate: Long): List<CategoryTotal>

    @Query("""
        SELECT category, SUM(amount) as total, COUNT(*) as count
        FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startOfMonth AND :endOfMonth
        GROUP BY category
        ORDER BY total DESC
    """)
    suspend fun getMonthlyCategorySummary(userId: String, startOfMonth: Long, endOfMonth: Long): List<CategorySummary>

    // Amount Calculations
    @Query("""
        SELECT SUM(amount) as total 
        FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startDate AND :endDate
    """)
    suspend fun getTotalExpensesByDateRange(userId: String, startDate: Long, endDate: Long): Double?

    @Query("""
        SELECT SUM(amount) as monthlyTotal 
        FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startOfMonth AND :endOfMonth
    """)
    suspend fun getMonthlyTotal(userId: String, startOfMonth: Long, endOfMonth: Long): Double?

    @Query("""
        SELECT AVG(amount) as averageExpense 
        FROM expenses 
        WHERE userId = :userId AND category = :category
    """)
    suspend fun getAverageExpenseByCategory(userId: String, category: ExpenseCategory): Double?

    // Recurring Expenses
    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId AND isRecurring = 1
        ORDER BY expenseDate DESC
    """)
    suspend fun getRecurringExpenses(userId: String): List<ExpenseEntity>

    @Query("""
        SELECT SUM(amount) as totalRecurring 
        FROM expenses 
        WHERE userId = :userId AND isRecurring = 1
    """)
    suspend fun getTotalRecurringExpenses(userId: String): Double?

    // Statistics
    @Query("SELECT COUNT(*) FROM expenses WHERE userId = :userId")
    suspend fun getExpenseCount(userId: String): Int

    @Query("""
        SELECT COUNT(*) FROM expenses 
        WHERE userId = :userId 
        AND expenseDate BETWEEN :startOfMonth AND :endOfMonth
    """)
    suspend fun getMonthlyExpenseCount(userId: String, startOfMonth: Long, endOfMonth: Long): Int

    /**
     * Retrieves expenses for a specific user within a given date range.
     * @param userId The ID of the user.
     * @param startDate The start of the date range (inclusive), in milliseconds.
     * @param endDate The end of the date range (inclusive), in milliseconds.
     * @return A Flow of a list of `ExpenseEntity` objects within the specified date range.
     */
    @Query("SELECT * FROM expenses WHERE userId = :userId AND expenseDate BETWEEN :startDate AND :endDate ORDER BY expenseDate DESC")
    fun getExpensesInDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<ExpenseEntity>>

    // Search
    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId 
        AND (description LIKE '%' || :searchQuery || '%' OR subcategory LIKE '%' || :searchQuery || '%')
        ORDER BY expenseDate DESC
    """)
    suspend fun searchExpenses(userId: String, searchQuery: String): List<ExpenseEntity>

    // Recent Expenses
    @Query("""
        SELECT * FROM expenses 
        WHERE userId = :userId 
        ORDER BY createdAt DESC 
        LIMIT :limit
    """)
    suspend fun getRecentExpenses(userId: String, limit: Int = 10): List<ExpenseEntity>
}

// Data classes for expense queries
data class CategoryTotal(
    val category: ExpenseCategory,
    val total: Double
)

data class CategorySummary(
    val category: ExpenseCategory,
    val total: Double,
    val count: Int
)*/
