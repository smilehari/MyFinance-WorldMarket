package com.harishri.financepal.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Watchlist : BottomNavItem("watchlist_route", "Watchlist", Icons.Default.List)
    object TopGainersLosers : BottomNavItem("top_movers_route", "Top Movers", Icons.Default.TrendingUp)
    object Portfolio : BottomNavItem("portfolio_route", "Portfolio", Icons.Default.AccountBalanceWallet)
    object Profile : BottomNavItem("profile_route", "Profile", Icons.Default.Person)
}