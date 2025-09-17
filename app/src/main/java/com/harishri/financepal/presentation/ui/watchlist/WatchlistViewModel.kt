package com.harishri.financepal.presentation.ui.watchlist

//import com.learn.financepal.data.local.dao.WatchlistDao
///import com.learn.financepal.data.local.entity.WatchlistEntity


/**
 * ViewModel for managing user's watchlist.
 * This class handles the UI logic and data synchronization for the watchlist screen.
 * It fetches data from a simulated API and stores it in the database.
 */
/*
@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistDao: WatchlistDao,
    private val stockRepository: StockRepository
) : ViewModel() {

    // Dummy user ID for demonstration purposes
    private val userId = ""

    /**
     * A StateFlow that holds the list of watchlist items for the current user.
     * It is exposed to the UI layer to observe real-time changes.
     */
    val watchlist: StateFlow<List<WatchlistEntity>> = watchlistDao.getAllWatchlistForUser(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        // Now the ViewModel simply tells the repository to sync the data.
        // It does not contain the logic for the network call itself.
        viewModelScope.launch {
            stockRepository.syncWatchlistFromNetwork(userId)
        }
    }

    /**
     * Fetches the latest price for a stock and returns it.
     */
    suspend fun getLatestPriceForSymbol(symbol: String): Double? {
        var latestPrice: Double? = null
        stockRepository.getLatestStockQuote(symbol).collect { price ->
            latestPrice = price
        }
        return latestPrice
    }
}
*/

