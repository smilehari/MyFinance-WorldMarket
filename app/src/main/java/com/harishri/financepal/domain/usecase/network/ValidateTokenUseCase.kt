package com.harishri.financepal.domain.usecase.network

import com.harishri.financepal.domain.model.NetworkResult
import com.harishri.financepal.domain.repositories.NetworkRepository
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(idToken: String): NetworkResult<Boolean> {
        return networkRepository.validateToken(idToken)
    }
}
