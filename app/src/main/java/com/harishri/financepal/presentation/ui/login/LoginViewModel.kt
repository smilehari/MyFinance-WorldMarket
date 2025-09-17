package com.harishri.financepal.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.model.DeviceInfo
import com.harishri.financepal.domain.model.LoginUiState
import com.harishri.financepal.domain.repositories.AuthRepository
import com.harishri.financepal.domain.usecase.auth.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    // This StateFlow will hold and manage the UI state for all authentication-related
    // screens. It's the single source of truth for the UI layer.
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        // We launch a coroutine to start observing the authentication state immediately.
        // This is the key to handling initial navigation.
        viewModelScope.launch {
            authRepository.getAuthState().collect { user ->
                // When a new user is emitted (or null), we update the UI state.
                val isLoggedIn = user != null
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoggedIn = isLoggedIn,
                    isInitialCheckDone = true, // We have now checked the initial state
                    isLoading = false,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Handles the result from a Google Sign-In attempt.
     * It delegates the actual sign-in and user saving logic to the repository.
     */
    fun handleSignInResult(idToken: String, account: GoogleSignInAccount) {
        viewModelScope.launch {
            // First, update the state to show a loading spinner
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // The repository now returns a simple Result<Unit>. The ViewModel
                // doesn't care about the result, because it's already observing the
                // database for changes.
                authRepository.signInWithGoogle(idToken)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Sign in failed. Check your connection.",
                    isInitialCheckDone = true // The check is done, just with an error
                )
                Log.e("LoginViewModel", "Sign in failed: ", e)
            }
        }
    }

    /**
     * Handles errors from the Google Sign-In UI component.
     */
    fun handleSignInResultError(exception: Exception) {
        Log.e("LoginViewModel", "handleSignInResultError: Google sign in error  ${exception.message}")
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = "Google sign in error: ${exception.message}",
            isInitialCheckDone = true
        )
    }
    /*fun handleSignInResult(idToken: String, account: GoogleSignInAccount) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            Log.d("LoginViewModel", "handleSignInResult")
            try {
                val result:Result<LoginUiState>  = signInWithGoogleUseCase(idToken,account)
                if (result.isSuccess) {
                    Log.d("LoginViewModel", "handleSignInResult -> sucess")
                    _uiState.value = LoginUiState.Success(mapGoogleAccountToUser(account))
                } else {
                    Log.d("LoginViewModel", "handleSignInResult -> failed")
                    _uiState.value = LoginUiState.Error("Sign in failed",
                        result.exceptionOrNull() as Exception?
                    )
                }
            }catch (e: Exception) {
                Log.e("LoginViewModel", "handleSignInResult -> exception", e)
                _uiState.value = LoginUiState.Error("Sign in failed. Check your connection.", e)
            }
        }
    }*/
    fun saveSignInResult(account: GoogleSignInAccount) {
        //TODO
        Log.d("LoginViewModel", "saveSignInResult")
    }


    /*fun handleSignInResultError(exception: Exception) {
        Log.e("LoginViewModel", "handleSignInResultError: Google sign in error  ${exception.message}")
        _uiState.value = LoginUiState.Error("Google sign in error ${exception.message} ",exception)
    }*/

    private fun mapGoogleAccountToUser(account: GoogleSignInAccount): UserEntity {
        return UserEntity(
            userId = account.id ?: "",
            displayName = account.displayName,
            email = account.email ?: "",
            photoUrl = account.photoUrl?.toString(),
            idToken = account.idToken,
            deviceInfo = DeviceInfo(
                //TODO get device info from device
                deviceModel = "Build.MODEL",
                osVersion ="Build.VERSION.RELEASE",
                appVersion = "1.0.0",
                deviceId = ""
            ),
            createdAt = System.currentTimeMillis(),
            lastLoginAt = System.currentTimeMillis(),
            isActive = true)
    }
}

