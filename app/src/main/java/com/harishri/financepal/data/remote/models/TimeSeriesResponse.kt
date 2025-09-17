package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable

data class TimeSeriesResponse(
    @SerialName("Meta Data")
    val metaData: TimeSeriesMetaDataResponse,

    @SerialName("Time Series (Daily)")
    val timeSeries: Map<String, DailyDataResponse>
)