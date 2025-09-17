package com.harishri.financepal.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor for handling rate limiting and API errors
 * Alpha Vantage has rate limits, so we need proper error handling
 */
class AlphaVantageErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Check for rate limiting (Alpha Vantage returns 200 but with error message)
        if (response.isSuccessful) {
            val responseBody = response.peekBody(Long.MAX_VALUE).string()

            when {
                responseBody.contains("Please subscribe to any of the premium plans") -> {
                    // API limit reached
                    throw ApiLimitExceededException("Alpha vantage API call frequency limit reached")
                }
                responseBody.contains("Invalid API call") -> {
                    throw InvalidApiCallException("Invalid API parameters")
                }
                responseBody.contains("Error Message") -> {
                    throw ApiErrorException("Alpha Vantage API error: Check your parameters")
                }
            }
        }

        return response
    }
}

class ApiLimitExceededException(message: String) : Exception(message)
class InvalidApiCallException(message: String) : Exception(message)
class ApiErrorException(message: String) : Exception(message)
