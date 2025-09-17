package com.harishri.financepal.presentation.viewmodels

/*

@HiltViewModel
class WatchlistViewModel  @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    //private val userManager: UserManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<StockQuoteResponse>>(emptyList())
    val searchResults: StateFlow<List<StockQuoteResponse>> = _searchResults.asStateFlow()

    init {
        observeWatchlist()
        observeLastSyncTime()
    }

    private fun observeWatchlist() {
        userManager.userName?.let { userId ->
            watchlistRepository.getWatchlistItems(userId)
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
                .onEach { items ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        watchlistItems = items,
                        error = null
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    private fun observeLastSyncTime() {
        watchlistRepository.getLastSyncTime()
            .onEach { timestamp ->
                _uiState.value = _uiState.value.copy(lastSyncTime = timestamp)
            }
            .launchIn(viewModelScope)
    }

    fun refreshPrices() {
        userManager.userName?.let { userId ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isRefreshing = true)

                watchlistRepository.refreshStockPrices(userId)
                    .fold(
                        onSuccess = {
                            _uiState.value = _uiState.value.copy(isRefreshing = false)
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(
                                isRefreshing = false,
                                error = error.message
                            )
                        }
                    )
            }
        }
    }

    fun addToWatchlist(symbol: String, companyName: String) {
        userManager.userName?.let { userId ->
            viewModelScope.launch {
                watchlistRepository.addToWatchlist(userId, symbol, companyName)
                    .fold(
                        onSuccess = {
                            // Success handled by the Flow observer
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(error = error.message)
                        }
                    )
            }
        }
    }

    fun removeFromWatchlist(symbol: String) {
        userManager.userName?.let { userId ->
            viewModelScope.launch {
                watchlistRepository.removeFromWatchlist(userId, symbol)
                    .fold(
                        onSuccess = {
                            // Success handled by the Flow observer
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(error = error.message)
                        }
                    )
            }
        }
    }

    fun searchStocks(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true)

            watchlistRepository.searchStocks(query)
                .fold(
                    onSuccess = { results ->
                        _searchResults.value = results
                        _uiState.value = _uiState.value.copy(isSearching = false)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isSearching = false,
                            error = error.message
                        )
                    }
                )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}*/
