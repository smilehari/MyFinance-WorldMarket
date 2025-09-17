package com.harishri.financepal.data.remote.interceptors

import com.harishri.financepal.data.local.preference.PreferencesManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url

        val requestBuilder = originalRequest.newBuilder()

        // Add API keys based on the host
        when {
            url.host.contains("alphavantage") -> {
                requestBuilder.url(
                    url.newBuilder()
                        //.addQueryParameter("apikey", API_KEY)
                        .build()
                )
            }
            url.host.contains("yahoo") -> {
                //TODO for any other provider like zerodha or yahoo
                // Yahoo Finance doesn't require API key for basic quotes
                // But add user agent for better success rate
                //requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Android) FinanceApp/1.0")
            }
        }
        // Use runBlocking to synchronously get the auth token from DataStore
        val token = runBlocking { preferencesManager.getAuthToken() }

        val authorizationHeaderValue = if (token.isNullOrEmpty()) "" else "Bearer $token"

        val finalRequestBuilder = requestBuilder
            .addHeader("Accept", "application/json")
            //.addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Connection", "keep-alive")

        // Add Authorization header only if the token is not empty
        if (authorizationHeaderValue.isNotEmpty()) {
            finalRequestBuilder.addHeader("Authorization", authorizationHeaderValue)
        }
        return chain.proceed(finalRequestBuilder.build())
    }
}