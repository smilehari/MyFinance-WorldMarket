package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class DailyDataResponse (
    @SerialName("1. open")
    val open: String,

    @SerialName("2. high")
    val high: String,

    @SerialName("3. low")
    val low: String,

    @SerialName("4. close")
    val close: String,

    @SerialName("5. volume")
    val volume: String

)