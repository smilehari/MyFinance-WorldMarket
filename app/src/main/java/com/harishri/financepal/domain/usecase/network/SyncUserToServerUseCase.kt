package com.harishri.financepal.domain.usecase.network

import com.harishri.financepal.domain.model.NetworkResult
import com.harishri.financepal.domain.model.User
import com.harishri.financepal.domain.repositories.NetworkRepository
import javax.inject.Inject

class SyncUserToServerUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(user: User): NetworkResult<String> {
        return networkRepository.syncUserToServer(user)
    }
}
