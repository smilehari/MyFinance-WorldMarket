package com.harishri.financepal.domain.usecase.user

import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase  @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<UserEntity?> {
        return repository.getCurrentUser()
    }

}