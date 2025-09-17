package com.harishri.financepal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("user_id") val userId: String,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String,
    @SerialName("photo_url") val photoUrl: String?,
    @SerialName("id_token") val idToken: String?,
    @SerialName("device_info") val deviceInfo: DeviceInfoDto,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("last_login_at") val lastLoginAt: Long
)
