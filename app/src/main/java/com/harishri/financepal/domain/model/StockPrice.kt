package com.harishri.financepal.domain.model

data class StockPrice(
    val symbol: String,
    val companyName: String,
    val currentPrice: Double,
    val previousClose: Double,
    val change: Double,
    val changePercent: Double,
    val volume: Long,
    val marketCap: Double? = null,
    val dayHigh: Double,
    val dayLow: Double,
    val currency: String,
    val exchange: String, // BSE, NSE
    val lastUpdated: Long,
    val isMarketOpen: Boolean = false
) {
    val isPositive: Boolean get() = change >= 0

    // Extract exchange from symbol (RELIANCE.BSE -> BSE)
    fun getExchangeFromSymbol(): String {
        return symbol.substringAfter(".", "")
    }

    // Get clean symbol (RELIANCE.BSE -> RELIANCE)
    fun getCleanSymbol(): String {
        return symbol.substringBefore(".")
    }
}
