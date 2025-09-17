package com.harishri.financepal.domain.usecase.auth

import com.harishri.financepal.domain.repositories.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase  @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<Unit> {
        return authRepository.signInWithGoogle(idToken)
    }
}