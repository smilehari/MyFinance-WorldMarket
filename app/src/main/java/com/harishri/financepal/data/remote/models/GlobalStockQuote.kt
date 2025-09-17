package com.harishri.financepal.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalStockQuote(
    @SerialName("01. symbol")
    val symbol: String,
    @SerialName("02. open")
    val open: String,
    @SerialName("03. high")
    val high: String,
    @SerialName("04. low")
    val low: String,
    @SerialName("05. price")
    val price: String,
    @SerialName("06. volume")
    val volume: String,
    @SerialName("07. latest trading day")
    val latestTradingDay: String,
    @SerialName("08. previous close")
    val previousClose: String,
    @SerialName("09. change")
    val change: String,
    @SerialName("10. change percent")
    val changePercent: String
)