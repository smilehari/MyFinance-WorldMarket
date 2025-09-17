package com.harishri.financepal.data.remote.interceptors

import com.harishri.financepal.data.remote.NetworkMonitor
import com.harishri.financepal.di.NetworkModule
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor(
    private val networkMonitor: NetworkMonitor
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Offline-first strategy
        if (!networkMonitor.isConnected()) {
            request = request.newBuilder()
                .cacheControl(
                    CacheControl.Builder()
                        .maxStale(NetworkModule.CACHE_MAX_STALE, TimeUnit.SECONDS)
                        .build()
                )
                .build()
        }

        val response = chain.proceed(request)

        // Cache strategy for online requests
        if (networkMonitor.isConnected()) {
            val cacheControl = CacheControl.Builder()
                .maxAge(NetworkModule.CACHE_MAX_AGE, TimeUnit.SECONDS)
                .build()

            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        return response
    }
}