package com.harishri.financepal.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Network connection interceptor for handling connectivity issues
 */
class ConnectivityInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            throw NetworkException("Network error: ${e.message}", e)
        }
    }
}
class NetworkException(message: String, cause: Throwable) : Exception(message, cause)