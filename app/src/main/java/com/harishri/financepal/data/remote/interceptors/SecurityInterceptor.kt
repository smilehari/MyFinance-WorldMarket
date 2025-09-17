package com.harishri.financepal.data.remote.interceptors

import android.util.Log
import com.harishri.financepal.BuildConfig
import com.harishri.financepal.data.local.device.DeviceSecurityChecker
import com.harishri.financepal.data.local.device.SecurityIssue
import okhttp3.Interceptor
import okhttp3.Response

class SecurityInterceptor(
    private val deviceSecurityChecker: DeviceSecurityChecker
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Check device security before making network requests
        val securityIssues = deviceSecurityChecker.checkSecurity()

        if (securityIssues.isNotEmpty()) {
            // Log security issues
            securityIssues.forEach { issue ->
                Log.w("SecurityInterceptor", "Security issue detected: $issue")
            }

            // In production, you might want to block requests for critical security issues
            if (securityIssues.contains(SecurityIssue.ROOTED_DEVICE) && BuildConfig.BLOCK_ROOTED_DEVICES) {
                throw SecurityException("Device security compromised")
            }
        }

        return chain.proceed(chain.request())
    }
}