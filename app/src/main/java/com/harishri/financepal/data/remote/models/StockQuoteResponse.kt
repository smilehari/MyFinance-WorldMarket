package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class StockQuoteResponse(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("shortname")
    val shortName: String?,
    @SerialName("longname")
    val longName: String?,
    @SerialName("exchange")
    val exchange: String?
)
