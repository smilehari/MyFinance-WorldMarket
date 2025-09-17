package com.harishri.financepal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.harishri.financepal.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount() : Int


    @Query("SELECT * FROM users WHERE isActive = 1 ORDER BY lastLoginAt DESC LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Query("SELECT userId FROM users WHERE isActive = 1 ORDER BY lastLoginAt DESC LIMIT 1")
    fun getCurrentUserId(): String

    @Query("SELECT * FROM users WHERE userId = :userId AND isActive = 1")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users ORDER BY lastLoginAt DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE isActive = 1)")
    fun isUserLoggedIn(): Flow<Boolean>

    @Query("UPDATE users SET isActive = 0")
    suspend fun deactivateAllUsers()

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUser(userId: String)

 /*

 @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount() : Int
 @Query("UPDATE users SET isActive = 0 WHERE userId = :userId")
    suspend fun deactivateUser(userId: String)

    @Query("UPDATE users SET lastLoginAt = :timestamp WHERE userId = :userId")
    suspend fun updateLastLogin(userId: String, timestamp: Long = System.currentTimeMillis()) :Int

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<UserEntity?>*/
}