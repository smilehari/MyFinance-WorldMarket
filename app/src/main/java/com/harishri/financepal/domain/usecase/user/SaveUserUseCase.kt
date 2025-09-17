package com.harishri.financepal.domain.usecase.user

import com.harishri.financepal.domain.model.User
import com.harishri.financepal.domain.repositories.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return repository.saveUser(user)
    }
}