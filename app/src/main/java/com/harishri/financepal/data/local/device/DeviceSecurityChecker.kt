package com.harishri.financepal.data.local.device

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceSecurityChecker @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun checkSecurity(): List<SecurityIssue> {
        val issues = mutableListOf<SecurityIssue>()

        if (isDeviceRooted()) {
            issues.add(SecurityIssue.ROOTED_DEVICE)
        }

        if (isMockLocationEnabled()) {
            issues.add(SecurityIssue.MOCK_LOCATION)
        }

        if (isVpnActive()) {
            issues.add(SecurityIssue.VPN_DETECTED)
        }

        if (isDebuggingEnabled()) {
            issues.add(SecurityIssue.DEBUG_ENABLED)
        }

        if (hasHookingFramework()) {
            issues.add(SecurityIssue.HOOKING_FRAMEWORK)
        }

        return issues
    }

    private fun isDeviceRooted(): Boolean {
        // Check for common root indicators
        val rootIndicators = listOf(
            "/system/app/Superuser.apk",
            "/system/xbin/which",
            "/system/bin/which",
            "/system/xbin/su",
            "/system/bin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        return rootIndicators.any { File(it).exists() } ||
                checkForRootBinaries() ||
                checkForRootPackages()
    }

    private fun checkForRootBinaries(): Boolean {
        try {
            val process = Runtime.getRuntime().exec("which su")
            return process.inputStream.bufferedReader().readText().isNotBlank()
        } catch (e: Exception) {
            return false
        }
    }

    private fun checkForRootPackages(): Boolean {
        val rootPackages = listOf(
            "com.noshufou.android.su",
            "com.noshufou.android.su.elite",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "com.yellowes.su",
            "com.topjohnwu.magisk"
        )

        return rootPackages.any { packageName ->
            try {
                context.packageManager.getPackageInfo(packageName, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }

    private fun isMockLocationEnabled(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
                val mode = appOpsManager.checkOp(
                    "android:mock_location",
                    android.os.Process.myUid(),
                    context.packageName
                )
                return mode == android.app.AppOpsManager.MODE_ALLOWED
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }

    private fun isVpnActive(): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                return networkCapabilities?.hasTransport(android.net.NetworkCapabilities.TRANSPORT_VPN) == true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    private fun isDebuggingEnabled(): Boolean {
        return android.provider.Settings.Global.getInt(
            context.contentResolver,
            android.provider.Settings.Global.ADB_ENABLED,
            0
        ) == 1
    }

    private fun hasHookingFramework(): Boolean {
        val hookingFrameworks = listOf(
            "de.robv.android.xposed.installer",
            "com.saurik.substrate",
            "com.android.vending.billing.InAppBillingService.LOCK"
        )

        return hookingFrameworks.any { framework ->
            try {
                context.packageManager.getPackageInfo(framework, 0)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }
}

enum class SecurityIssue {
    ROOTED_DEVICE,
    MOCK_LOCATION,
    VPN_DETECTED,
    DEBUG_ENABLED,
    HOOKING_FRAMEWORK
}