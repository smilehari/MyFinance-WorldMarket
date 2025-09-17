package com.harishri.financepal.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harishri.financepal.data.local.entity.WatchlistEntity
import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.data.remote.utils.Result
import com.harishri.financepal.domain.repositories.UserRepository
import com.harishri.financepal.domain.usecase.watchlist.AddStockToWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.GetWatchlistStocksUseCase
import com.harishri.financepal.domain.usecase.watchlist.RemoveStockFromWatchlistUseCase
import com.harishri.financepal.domain.usecase.watchlist.SearchStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistStocksUseCase: GetWatchlistStocksUseCase,
    private val addStockToWatchlistUseCase: AddStockToWatchlistUseCase,
    private val searchStocksUseCase: SearchStocksUseCase,
    private val removeStockFromWatchlistUseCase: RemoveStockFromWatchlistUseCase,
    val userRepository: UserRepository
) : ViewModel() {
    //TODO this is returning a temporary userID need to change this to fetch
    // the actual value from the user table by adding a suspend function
    //val userId: String =userRepository.getCurrentUserId()
    //lateinit var userId: String
    private val userId = userRepository.getCurrentUserId()

    // UI state for the watchlist
    //private val _watchlistState = MutableStateFlow<WatchlistState>(WatchlistState.Loading)
    //val watchlistState: StateFlow<WatchlistState> = _watchlistState.asStateFlow()
    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    //Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Search results
    private val _searchResults = MutableStateFlow<List<StockSearchResult>>(emptyList())
    val searchResults: StateFlow<List<StockSearchResult>> = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {

            //")
            // Load watchlist stocks
            loadWatchlistStocks()
        }


        // Setup search functionality with debouncing
        searchQuery
            .debounce(500) // Wait 300ms after user stops typing
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    searchStocks(query)
                } else {
                    _searchResults.value = emptyList()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadWatchlistStocks() {
        getWatchlistStocksUseCase(userId)
            .onEach { stocks ->
                _uiState.value = _uiState.value.copy(
                    stocks = stocks,
                    isLoading = false
                )
            }
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    error = exception.message,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun searchStocks(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSearching = true)

            val searchResultResponseList = searchStocksUseCase(query)
            when (searchResultResponseList) {
                is Result.Success -> {
                    Log.d("WatchlistViewModel", "WatchlistViewModel -> searchStocks Success:$$$ Hari ${searchResultResponseList.data}")
                    Log.d("WatchlistViewModel", "WatchlistViewModel -> searchStocks Success:$$$ Hari ${searchResults.value}")
                    _searchResults.value = searchResultResponseList.data
                    _uiState.value = _uiState.value.copy(isSearching = false)

                }
                is Result.Error -> {
                    Log.e("WatchlistViewModel", "WatchlistViewModel -> searchStocks Error:$$$ ${searchResultResponseList.message}")
                    _uiState.value =_uiState.value.copy(
                        error = "Search failed: ${searchResultResponseList.message}",
                        isSearching = false
                    )
                }
            }
        }
    }

    fun addStockToWatchlist(symbol: String,name:String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val addedSuccessResult = addStockToWatchlistUseCase(symbol, userId, name)
            when (addedSuccessResult) {
                is Result.Success -> {
                    _searchQuery.value = ""
                    _searchResults.value = emptyList()
                    Log.d(
                        "WatchlistViewModel",
                        "addStockToWatchlist Success:$$$ "
                    )
                }

                is Result.Error -> {
                    Log.d(
                        "WatchlistViewModel",
                        "searchStocks Error:$$$ ${addedSuccessResult.message}"
                    )
                    _uiState.value =
                        _uiState.value.copy(error = "Failed to add stock:  ${addedSuccessResult.message}",)
                }
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun removeStockFromWatchlist(symbol: String) {
        viewModelScope.launch {
            val removedResult = removeStockFromWatchlistUseCase(userId,symbol)
            when (removedResult) {
                is Result.Success -> {
                    Log.d("WatchlistViewModel", "searchStocks Success:$$$ $userId ${symbol }")
                    //_uiState.value = SearchState.Success(result.data)
                }
                is Result.Error -> {
                    Log.d("WatchlistViewModel", "searchStocks Error:$$$ ${removedResult.message}")
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to remove stock: ${removedResult.message}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }



}

// UI State data class
data class WatchlistUiState(
    val stocks: List<WatchlistEntity> = emptyList(),
    val isLoading: Boolean = true,
    val isSearching: Boolean = false,
    val error: String? = null
)