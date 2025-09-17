package com.harishri.financepal.data.local.entity

//Transaction history buy/sell, avg price, Realized vs Unrealized P&L
//Essential for transaction logging feature
/*
@Entity(
    tableName = "transactions"
)

data class TransactionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val symbol: String,
    val transactionType: TransactionType,  // BUY, SELL
    val quantity: Double,
    val price: Double,                     // Price per share at transaction
    val totalAmount: Double,               // quantity * price + charges
    val charges: Double = 0.0,             // Brokerage, taxes, etc.
    val transactionDate: Long,             // User can set custom date
    val notes: String? = null,             // Optional user notes
    val createdAt: Long = System.currentTimeMillis()
)
*/