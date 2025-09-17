package com.harishri.financepal.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class DeviceInfo(
    val deviceModel: String,
    val osVersion: String,
    val appVersion: String,
    val deviceId: String? = null
) : Parcelable
