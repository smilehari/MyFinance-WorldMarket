package com.harishri.financepal.presentation.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harishri.financepal.presentation.nav.BottomNavItem
import com.harishri.financepal.presentation.ui.portfolio.PortfolioScreen
import com.harishri.financepal.presentation.ui.profile.ProfileScreen
import com.harishri.financepal.presentation.ui.watchlist.WatchlistScreen

import com.harishri.financepal.presentation.ui.topmover.TopMoversScreen
import com.harishri.financepal.presentation.viewmodels.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)

/**
 * The main entry point for the application's UI, including bottom navigation.
 */
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    BottomNavItem.Watchlist,
                    BottomNavItem.TopGainersLosers,
                    BottomNavItem.Portfolio,
                    BottomNavItem.Profile
                )

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination to avoid building up a large stack
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when reselecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Watchlist.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Watchlist.route) {
                val viewModel: WatchlistViewModel = hiltViewModel()
                WatchlistScreen(viewModel = viewModel)
            }
            composable(BottomNavItem.TopGainersLosers.route) { TopMoversScreen() }
            composable(BottomNavItem.Portfolio.route) { PortfolioScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}