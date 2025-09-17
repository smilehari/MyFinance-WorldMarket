package com.harishri.financepal.domain.model

import com.harishri.financepal.data.local.entity.UserEntity


/**
 * Represents the entire state of the login screen and overall authentication status.
 *
 * @param user The authenticated user object, or null if unauthenticated.
 * @param isLoading A flag to show a loading indicator during sign-in attempts.
 * @param errorMessage An error message to display to the user.
 * @param isInitialCheckDone A flag to indicate that the app's initial auth state check is complete.
 * @param isLoggedIn A convenience flag indicating if the user is authenticated.
 */
data class LoginUiState(
    val user: UserEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isInitialCheckDone: Boolean = false,
    val isLoggedIn: Boolean = false
)
/*sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: Any? = null) : LoginUiState()
    object Unauthenticated : LoginUiState()
    data class Error(val message: String="",  val exception : Exception? = null) : LoginUiState()
}*/
