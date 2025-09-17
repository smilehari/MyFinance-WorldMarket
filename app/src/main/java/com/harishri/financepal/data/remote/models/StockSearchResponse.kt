package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockSearchResponse(
    @SerialName("bestMatches")
    val bestMatches: List<StockSearchResult>
)
