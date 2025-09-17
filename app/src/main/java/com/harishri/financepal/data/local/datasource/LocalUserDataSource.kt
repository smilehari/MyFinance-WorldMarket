package com.harishri.financepal.data.local.datasource

import android.util.Log
import com.harishri.financepal.data.local.dao.UserDao
import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.data.local.entity.toDomainModel
import com.harishri.financepal.data.local.entity.toEntity
import com.harishri.financepal.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserDataSource @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            userDao.insert(user.toEntity())
            Log.d("LocalUserDataSource","User saved locally: ${user.userId}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to save user locally $e")
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: String): User? {
        return try {
            userDao.getUserById(userId)?.toDomainModel()
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to get user by id: $userId $e")
            null
        }
    }

    fun getCurrentUser(): Flow<UserEntity?> {
        return userDao.getCurrentUser()
    }

    fun getCurrentUserId(): String{
        return "coObS28gUGQ5128DVTqkitvzV812"
        //return userDao.getCurrentUserId()
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun updateUser(user: User): Result<Unit> {
        return try {
            userDao.update(user.toEntity())
            Log.d("LocalUserDataSource","User updated locally: ${user.userId}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to update user locally $e")
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            userDao.deleteUser(userId)
            Log.d("LocalUserDataSource","User deleted locally: $userId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to delete user locally $e")
            Result.failure(e)
        }
    }

    suspend fun clearAllUsers(): Result<Unit> {
        return try {
            userDao.clearAllUsers()
            Log.d("LocalUserDataSource","All users cleared locally")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to clear all users locally $e")
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return userDao.isUserLoggedIn()
    }

    suspend fun deactivateAllUsers(): Result<Unit> {
        return try {
            userDao.deactivateAllUsers()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("LocalUserDataSource", "Failed to deactivate all users $e")
            Result.failure(e)
        }
    }

}