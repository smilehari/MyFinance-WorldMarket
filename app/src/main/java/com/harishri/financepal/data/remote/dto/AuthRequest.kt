package com.harishri.financepal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("id_token") val idToken: String,
    @SerialName("device_info") val deviceInfo: DeviceInfoDto
)
