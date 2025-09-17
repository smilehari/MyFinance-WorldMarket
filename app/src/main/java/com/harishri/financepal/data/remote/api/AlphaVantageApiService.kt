package com.harishri.financepal.data.remote.api


import com.harishri.financepal.data.remote.models.CompanyOverviewResponse
import com.harishri.financepal.data.remote.models.GlobalStockQuoteResponse
import com.harishri.financepal.data.remote.models.StockSearchResponse
import com.harishri.financepal.data.remote.models.TimeSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApiService {
    /**
     * Fetches the latest stock quote from the Alpha Vantage API.
     * @param function The API function to call (e.g., "GLOBAL_QUOTE").
     * @param symbol The stock symbol (e.g., "AAPL").
     * @param apiKey The user's Alpha Vantage API key.
     * @return A response containing the latest quote data.
     */
    @GET("query")
    suspend fun getGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Response<GlobalStockQuoteResponse>

    // Search for stocks
    @GET("query")
    suspend fun searchSymbols(
        @Query("function") function: String = "SYMBOL_SEARCH",
        @Query("keywords") keywords: String,
        @Query("apikey") apikey: String
    ): Response<StockSearchResponse>




    /**
     * Fetches the user's watchlist from the API.

     */
    //@GET("users/{userId}/watchlist")
    //suspend fun getWatchlist(@Path("userId") userId: String): Response<List<WatchlistEntity>>

    //the following will go in zerodha api calls
//
//    /**
//     * Fetches the user's holdings from the API.
//     * @param userId The ID of the user.
//     * @return A list of `HoldingEntity` objects.
//     */
//    @GET("users/{userId}/holdings")
//    suspend fun getHoldings(@Path("userId") userId: String): Response<List<HoldingEntity>>
//
//    /**
//     * Fetches the user's transactions from the API.
//     * @param userId The ID of the user.
//     * @return A list of `TransactionEntity` objects.
//     */
//    @GET("users/{userId}/transactions")
//    suspend fun getTransactions(@Path("userId") userId: String): Response<List<TransactionEntity>>


    // Get historical daily data
    @GET("query")
    suspend fun getDailyTimeSeries(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("outputsize") outputsize: String = "compact", // compact = last 100 days, full = 20+ years
        @Query("apikey") apikey: String
    ): TimeSeriesResponse

    // Get intraday data (for real-time updates)
    @GET("query")
    suspend fun getIntradayTimeSeries(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5min", // 1min, 5min, 15min, 30min, 60min
        @Query("apikey") apikey: String
    ): Map<String, Any> // Generic response for intraday

    // Get company overview (includes company name, market cap, etc.)
    @GET("query")
    suspend fun getCompanyOverview(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String
    ): CompanyOverviewResponse
}