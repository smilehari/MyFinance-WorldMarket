package com.harishri.financepal.domain.repositories

import com.harishri.financepal.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    fun getAuthState(): Flow<UserEntity?>
    suspend fun refreshToken(): Result<String>
}
