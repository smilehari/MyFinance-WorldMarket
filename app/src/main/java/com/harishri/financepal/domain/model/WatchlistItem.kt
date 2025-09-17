package com.harishri.financepal.domain.model

data class WatchlistItem(
    val id: Long = 0,
    val userId: String,
    val symbol: String,
    val companyName: String,
    val addedAt: Long,
    val sortOrder: Int = 0,
    val stockPrice: StockPrice? = null
)
