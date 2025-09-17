package com.harishri.financepal.domain.usecase.auth

import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<UserEntity?> {
        return authRepository.getAuthState()
    }
}
