package com.harishri.financepal.presentation.state

//import com.learn.financepal.data.local.entity.PriceAlertEntity
//import com.learn.financepal.data.local.entity.StockPriceEntity
import com.harishri.financepal.domain.model.WatchlistItem
import kotlin.collections.isNotEmpty

data class WatchlistUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSearching: Boolean = false,
    val watchlistItems: List<WatchlistItem> = emptyList(),
    val lastSyncTime: Long = 0,
    //val refreshSettings: RefreshSettings = RefreshSettings(),
    //val triggeredAlerts: List<PriceAlertEntity> = emptyList(),
    val isAppActive: Boolean = true,
    val error: String? = null
) {
    val hasWatchlistItems: Boolean get() = watchlistItems.isNotEmpty()
    val isOffline: Boolean get() = lastSyncTime > 0 && (System.currentTimeMillis() - lastSyncTime) > 300_000 // 5 minutes
    //val hasTriggeredAlerts: Boolean get() = triggeredAlerts.isNotEmpty()

    // Get top gainers and losers
    val topGainers: List<WatchlistItem> get() = watchlistItems
        .filter { it.stockPrice?.changePercent ?: 0.0 > 0 }
        .sortedByDescending { it.stockPrice?.changePercent }
        .take(3)

    val topLosers: List<WatchlistItem> get() = watchlistItems
        .filter { it.stockPrice?.changePercent ?: 0.0 < 0 }
        .sortedBy { it.stockPrice?.changePercent }
        .take(3)
}

// Extension functions for entity mapping
/*
fun StockPriceEntity.toDomainModel(): StockPrice {
    return StockPrice(
        symbol = symbol,
        companyName = companyName,
        currentPrice = currentPrice,
        previousClose = previousClose,
        change = change,
        changePercent = changePercent,
        dayHigh = dayHigh,
        dayLow = dayLow,
        currency = currency,
        exchange = exchange,
        lastUpdated = lastUpdated,
        isMarketOpen = isMarketOpen,
        volume = volume,
        marketCap = marketCap
    )
}

fun StockPrice.toEntity(): StockPriceEntity {
    return StockPriceEntity(
        symbol = symbol,
        companyName = companyName,
        currentPrice = currentPrice,
        previousClose = previousClose,
        change = change,
        changePercent = changePercent,
        dayHigh = dayHigh,
        dayLow = dayLow,
        currency = currency,
        exchange = exchange,
        lastUpdated = lastUpdated,
        isMarketOpen = isMarketOpen,
        volume = volume,
        marketCap = marketCap
    )
}*/
