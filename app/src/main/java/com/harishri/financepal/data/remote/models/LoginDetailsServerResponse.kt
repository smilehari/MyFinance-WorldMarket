package com.harishri.financepal.data.remote.models

data class LoginDetailsServerResponse(
    val success: Boolean,
    val message: String,
    val data: Any?
)