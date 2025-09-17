package com.harishri.financepal.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RateLimitInterceptor : Interceptor {

    private val requestTimes = mutableMapOf<String, MutableList<Long>>()
    private val maxRequestsPerMinute = mapOf(
        "alphavantage.co" to 5,
        "finance.yahoo.com" to 100 // More lenient for Yahoo
    )

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val host = request.url.host

        val maxRequests = maxRequestsPerMinute.entries
            .find { host.contains(it.key) }?.value ?: 60

        val now = System.currentTimeMillis()
        val oneMinuteAgo = now - 60_000

        // Clean old requests
        val hostRequests = requestTimes.getOrPut(host) { mutableListOf() }
        hostRequests.removeAll { it < oneMinuteAgo }

        // Check rate limit
        if (hostRequests.size >= maxRequests) {
            val oldestRequest = hostRequests.minOrNull() ?: 0
            val waitTime = 60_000 - (now - oldestRequest)

            if (waitTime > 0) {
                Thread.sleep(waitTime)
            }
        }

        // Record this request
        hostRequests.add(now)

        return chain.proceed(request)
    }
}