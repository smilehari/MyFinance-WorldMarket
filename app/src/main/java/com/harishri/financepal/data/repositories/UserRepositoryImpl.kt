package com.harishri.financepal.data.repositories

import com.harishri.financepal.data.local.datasource.LocalUserDataSource
import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.model.User
import com.harishri.financepal.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalUserDataSource
) : UserRepository {

    override suspend fun saveUser(user: User): Result<Unit> {
        return localDataSource.saveUser(user)
    }

    override suspend fun getUserById(userId: String): User? {
        return localDataSource.getUserById(userId)
    }
    override fun getCurrentUserId(): String {
        return localDataSource.getCurrentUserId()
    }

    override fun getCurrentUser(): Flow<UserEntity?> {
        return localDataSource.getCurrentUser()
    }

    override fun getAllUsers(): Flow<List<User>> {
        return localDataSource.getAllUsers()
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return localDataSource.updateUser(user)
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        return localDataSource.deleteUser(userId)
    }

    override suspend fun clearAllUsers(): Result<Unit> {
        return localDataSource.clearAllUsers()
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return localDataSource.isUserLoggedIn()
    }
}
