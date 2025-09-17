package com.harishri.financepal.data.remote.models

// Historical data point
data class HistoricalDataPoint(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long
)