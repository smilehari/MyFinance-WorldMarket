package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Data class to represent the Alpha Vantage Quote response
@Serializable
data class GlobalStockQuoteResponse(
    @SerialName("Global Quote")
    val globalQuote: GlobalStockQuote?
)