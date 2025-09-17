package com.harishri.financepal.data.local.entity

//Live price updates, Offline viewing
//Caches real-time stock prices from APIs - Reduces API calls and enables offline viewing
//Powers your live data feature
/*
@Entity(
    tableName = "stock_prices",
    //indices = [Index(value = ["symbol"], unique = true)]
)
data class StockPriceEntity(
    @PrimaryKey
    val symbol: String,
    val companyName: String,
    val currentPrice: Double,
    val previousClose: Double,
    val dayHigh: Double,
    val dayLow: Double,
    val change: Double,              // Absolute change
    val changePercent: Double,             // Percentage change
    val volume: Long,
    val marketCap: Double? = null,
    val exchange: String,
    val currency: String = "INR",
    val lastUpdated: Long = System.currentTimeMillis(),
    val isMarketOpen: Boolean = true
)

 */