package com.harishri.financepal.domain.repositories

import com.harishri.financepal.data.local.entity.WatchlistEntity
import com.harishri.financepal.data.remote.models.GlobalStockQuoteResponse
import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.data.remote.utils.Result
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    suspend fun searchStocks(query: String): Result<List<StockSearchResult>>
    suspend fun getGlobalQuote(symbol: String): Result<GlobalStockQuoteResponse>
    fun getWatchlistStocks(userId: String): Flow<List<WatchlistEntity>>
    suspend fun addStockToWatchlist(symbol: String, userId: String, name: String): Result<Unit>
    suspend fun removeFromWatchlist(userId: String, symbol: String): Result<Unit>
    suspend fun getWatchlistItem(userId: String, symbol: String): WatchlistEntity?

    //TODO
    fun updateAllWatchlistStocks(): Result<Unit>

    /*suspend fun refreshStockPrices(userId: String): Result<Unit>
    suspend fun getStockPrice(symbol: String): Result<StockPrice>
    suspend fun searchStocks(query: String): Result<List<StockQuoteResponse>>
    fun getLastSyncTime(): Flow<Long>*/

    suspend fun reorderWatchlist(fromIndex: Int, toIndex: Int): Result<Unit>
}