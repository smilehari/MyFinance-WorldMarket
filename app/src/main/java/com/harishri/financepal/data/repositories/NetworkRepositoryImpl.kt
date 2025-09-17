package com.harishri.financepal.data.repositories

import com.harishri.financepal.data.remote.datasource.RemoteUserDataSource
import com.harishri.financepal.domain.model.NetworkResult
import com.harishri.financepal.domain.model.User
import com.harishri.financepal.domain.repositories.NetworkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteUserDataSource
) : NetworkRepository {

    override suspend fun syncUserToServer(user: User): NetworkResult<String> {
        return remoteDataSource.syncUserToServer(user)
    }

    override suspend fun fetchUserFromServer(userId: String): NetworkResult<User> {
        return remoteDataSource.fetchUserFromServer(userId)
    }

    override suspend fun validateToken(idToken: String): NetworkResult<Boolean> {
        return remoteDataSource.validateToken(idToken)
    }
}
