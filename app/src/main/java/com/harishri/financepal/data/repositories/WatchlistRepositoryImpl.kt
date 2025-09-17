package com.harishri.financepal.data.repositories

import android.util.Log
import com.harishri.financepal.data.local.datasource.WatchlistLocalDataSource
import com.harishri.financepal.data.local.entity.WatchlistEntity
import com.harishri.financepal.data.remote.datasource.WatchlistRemoteDataSource
import com.harishri.financepal.data.remote.models.GlobalStockQuoteResponse
import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.data.remote.utils.Result
import com.harishri.financepal.domain.repositories.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    val remoteDataSource: WatchlistRemoteDataSource,
    val localDataSource: WatchlistLocalDataSource
    //private val watchlistDao: WatchlistDao
) : WatchlistRepository {

    override suspend fun getGlobalQuote(symbol: String): Result<GlobalStockQuoteResponse> {
        return remoteDataSource.getGlobalQuote(symbol)
    }
    override suspend fun searchStocks(query: String): Result<List<StockSearchResult>> {
        return remoteDataSource.searchStocks(query)
    }

    override suspend fun addStockToWatchlist(symbol: String, userId: String, name: String): Result<Unit> {
        Log.d("WatchlistRepositoryImpl", "addStockToWatchlist called")
        if (localDataSource.isStockInWatchlist(userId,symbol)) {
            return Result.Error("Stock is already in watchlist")
        }
        val result = remoteDataSource.getGlobalQuote(symbol)
        Log.d("WatchlistRepositoryImpl", "addStockToWatchlist  getGlobalQuote called")
        if (result is Result.Success) {
            Log.d("WatchlistRepositoryImpl", "addStockToWatchlist  getGlobalQuote Result.Success result.data")
            val sortOrder = localDataSource.getNextSortOrder(userId)
            val watchlistEntity = mapToWatchlistEntity(result.data, userId, name, sortOrder)
            if (watchlistEntity != null) {
                localDataSource.insertWatchlistItem(watchlistEntity)
                return Result.Success(
                    data = Unit
                )
            }else{
                return Result.Error("Failed to add stock to watchlist Stock is Null or empty")
            }
        }else{
            return Result.Error("Failed to add stock to watchlist "+(result as Error).message)
        }
    }

    override fun getWatchlistStocks(userId: String): Flow<List<WatchlistEntity>> {
        return localDataSource.getWatchlistItems(userId)
    }

    override fun updateAllWatchlistStocks(): Result<Unit>{
        //TODO("Not yet implemented")
        //1. Add Use case for Refresh
        //fetch all stock details see if a relevant API is available or else
        //https://www.alphavantage.co/documentation/ Realtime Bulk Quotes API is premium in alphavantage
        //fetch one by one and update, concurrent calls and upodate in background
        //TODO Display last updated date and time in Watch list page -> List View
        return Result.Success(data = Unit)
    }

    override suspend fun removeFromWatchlist(
        userId: String,
        symbol: String
    ): Result<Unit> {
        return try { localDataSource.deleteWatchlistItem(userId,symbol)
            Result.Success(Unit)
        }catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getWatchlistItem(
        userId: String,
        symbol: String
    ): WatchlistEntity? {
        return localDataSource.getWatchlistItem(userId, symbol)
    }

    override suspend fun reorderWatchlist(fromIndex: Int, toIndex: Int): Result<Unit> {
        return try {
            //TODO : Implementation for reordering stocks
            // This would involve updating positions in database
            Result.Success(Unit)
        }catch (e: Exception) {
            Result.Error(e.message)
        }
    }
    private fun mapToWatchlistEntity(response: GlobalStockQuoteResponse, userId: String,name: String, sortOrder: Int ): WatchlistEntity? {
        val quote = response.globalQuote
        Log.d("WatchlistRepositoryImpl", "mapToWatchlistEntity called $quote")
        if (quote != null) {
            return WatchlistEntity(
                symbol = quote.symbol.orEmpty(), //Primary key
                name = name,
                userId = userId,
                currentPrice = parsePrice(quote.price),
                previousClose = parsePrice(quote.previousClose),
                change = parsePrice(quote.change),
                changePercent = parsePrice(quote.changePercent),
                volume = quote.volume.toLongOrNull(),
                marketCap = null,
                sortOrder = sortOrder,
                lastPriceUpdate = System.currentTimeMillis()
            )
        }
        return null
    }
    // Helper functions to safely parse API responses
    private fun parsePrice(priceString: String): Double {
        return priceString.toDoubleOrNull() ?: 0.0
    }

    private fun parsePercentage(percentString: String): Double {
        return percentString.removeSuffix("%").toDoubleOrNull() ?: 0.0
    }


}