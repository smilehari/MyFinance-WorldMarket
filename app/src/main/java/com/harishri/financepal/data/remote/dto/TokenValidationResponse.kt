package com.harishri.financepal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenValidationResponse(
    @SerialName("is_valid") val isValid: Boolean,
    @SerialName("user_id") val userId: String?
)
