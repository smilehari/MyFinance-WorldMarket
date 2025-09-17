package com.harishri.financepal.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlphaVantageErrorResponse(
    @SerialName("Error Message")
    val errorMessage: String? = null,

    @SerialName("Note")
    val note: String? = null,

    @SerialName("Information")
    val information: String? = null
)