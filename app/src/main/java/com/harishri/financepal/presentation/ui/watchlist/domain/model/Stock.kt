package com.harishri.financepal.presentation.ui.watchlist.domain.model

// Domain model for Stock
data class Stock(
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val previousClose: Double,
    val change: Double,
    val changePercent: Double,
    val volume: Long,
    val marketCap: Long? = null,
    val lastPriceUpdate: Long = System.currentTimeMillis()
) {
    val isPositive: Boolean get() = change >= 0
}