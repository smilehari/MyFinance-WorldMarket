package com.harishri.financepal.domain.repositories

import com.harishri.financepal.domain.model.NetworkResult
import com.harishri.financepal.domain.model.User

interface NetworkRepository {
    suspend fun syncUserToServer(user: User): NetworkResult<String>
    suspend fun fetchUserFromServer(userId: String): NetworkResult<User>
    suspend fun validateToken(idToken: String): NetworkResult<Boolean>

}