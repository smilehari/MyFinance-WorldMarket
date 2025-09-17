package com.harishri.financepal.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId:String,
    val name: String?,
    val email: String,
    val photoUrl: String?,
    val idToken: String?,
    val deviceInfo: DeviceInfo,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) : Parcelable
