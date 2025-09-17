package com.harishri.financepal.presentation.state

sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    data class Success(val message: String) : NetworkState()
    data class Error(val message: String) : NetworkState()
}
