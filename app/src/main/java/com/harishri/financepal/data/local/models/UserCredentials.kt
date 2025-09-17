package com.harishri.financepal.data.local.models

data class UserCredentials (
    val userId: String,
    val name: String?,
    val email: String,
    val photoUrl: String?,
    val idToken: String?,
    val timestamp: Long = System.currentTimeMillis()
)
