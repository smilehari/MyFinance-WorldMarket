package com.harishri.financepal.domain.usecase.user

import com.harishri.financepal.domain.model.User
import com.harishri.financepal.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return userRepository.updateUser(user.copy(lastLoginAt = System.currentTimeMillis()))
    }
}