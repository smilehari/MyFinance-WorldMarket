package com.harishri.financepal.domain.repositories

import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun getUserById(userId: String): User?
    /**
     * Gets the ID of the currently active user.
     * This method would typically interact with an authentication service, but as of now we look for the local DB active status.
     * @return The ID of the current user.
     */
    fun getCurrentUserId(): String
    fun getCurrentUser(): Flow<UserEntity?>
    fun getAllUsers(): Flow<List<User>>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun clearAllUsers(): Result<Unit>
    fun isUserLoggedIn(): Flow<Boolean>

}