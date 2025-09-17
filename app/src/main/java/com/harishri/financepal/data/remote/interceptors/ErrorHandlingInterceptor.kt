package com.harishri.financepal.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ErrorHandlingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        try {
            val response = chain.proceed(request)

            // Handle specific HTTP errors
            when (response.code) {
                429 -> {
                    // Rate limited - wait and retry once
                    Thread.sleep(2000)
                    return chain.proceed(request)
                }
                500, 502, 503, 504 -> {
                    // Server errors - retry once after delay
                    Thread.sleep(1000)
                    return chain.proceed(request)
                }
            }

            return response

        } catch (e: Exception) {
            // Log network errors for debugging
            Log.e("NetworkError", "Request failed: ${request.url}", e)
            throw e
        }
    }
}