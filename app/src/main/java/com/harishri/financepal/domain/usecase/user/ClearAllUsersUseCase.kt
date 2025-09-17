package com.harishri.financepal.domain.usecase.user

import com.harishri.financepal.domain.repositories.UserRepository
import javax.inject.Inject

class ClearAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.clearAllUsers()
    }
}
