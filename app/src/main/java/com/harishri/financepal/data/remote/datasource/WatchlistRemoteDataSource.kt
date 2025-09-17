package com.harishri.financepal.data.remote.datasource

import android.util.Log
import com.harishri.financepal.data.remote.api.AlphaVantageApiService
import com.harishri.financepal.data.remote.models.GlobalStockQuoteResponse
import com.harishri.financepal.data.remote.models.StockSearchResult
import com.harishri.financepal.data.remote.utils.Result
import com.harishri.financepal.di.AlphaVantageApiKey
import java.io.IOException
import javax.inject.Inject


class WatchlistRemoteDataSource @Inject constructor(
    private val apiService: AlphaVantageApiService,
    @AlphaVantageApiKey val apiKey: String
) {

    /*suspend fun searchStocks(query: String): Result<StockSearchResponse>{
        return try {
            val response = apiService.searchSymbols(keywords = query, apikey = apiKey)
            Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks Result.Success $$$ ${response.bestMatches}")
            response.bestMatches.forEach { match ->
                Log.d("Stock", "WatchlistRemoteDataSource  Symbol: ${match.symbol}, Name: ${match.name}")
            }
            //Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks Result.Success $$$ ${response}")
            Result.Success(response)
        } catch (e: IOException) {
            Log.e("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks Result.IOException $$$ Network error occurred ${e.message}")
             Result.Error(e.message ?: "Network error occurred")
        } catch (e: Exception) {
            Log.e("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks Result.Exception $$$ An unknown error occurred ${e.message}")
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }*/

    suspend fun searchStocks(symbol: String): Result<List<StockSearchResult>> {
        return try {
            val response = apiService.searchSymbols(keywords = symbol, apikey = apiKey)
            Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks Result.Success $$$ ${response.isSuccessful}")
            Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks response.code =  $$$ ${response.code()}")
            if (response.isSuccessful) {
                val body = response.body()?.bestMatches
                Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks body != null =  $$$ ${response.code()}")
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("API returned a null body.")
                }
            } else {
                Log.e("StockRemoteDataSource", "API call failed with code: ${response.code()}")
                Result.Error("API call failed: ${response.message()}")
            }
        } catch (e: Exception) {
            // Log the exception details to the console
            Log.e("StockRemoteDataSource", "JSON Parsing Error: ${e.message}", e)
            Result.Error("Failed to parse stock search data: ${e.message}")
        }
    }

    suspend fun getGlobalQuote(symbol: String): Result<GlobalStockQuoteResponse> {
        return try {
            val response = apiService.getGlobalQuote(symbol = symbol, apiKey = apiKey)
            Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  getGlobalQuote Result.Success ${response}")
            Result.Success(response)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  searchStocks body != null =  $$$ ${response.code()}")
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("API returned a null body.")
                }
            } else {
                Log.e("StockRemoteDataSource", "API call failed with code: ${response.code()}")
                Result.Error("API call failed: ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  getGlobalQuote Result.IOException $$$ Network error occurred ${e.message}")
            Result.Error(e.message ?: "Network error occurred")
        } catch (e: Exception) {
            Log.e("WatchlistRemoteDataSource", "WatchlistRemoteDataSource  getGlobalQuote Result.Exception $$$ An unknown error occurred ${e.message}")
            Result.Error(e.message ?: "An unknown error occurred")
        }
    }
}