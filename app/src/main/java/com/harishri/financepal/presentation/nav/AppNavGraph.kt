package com.harishri.financepal.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harishri.financepal.presentation.ui.dashboard.DashboardScreen
import com.harishri.financepal.presentation.ui.login.LoginScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "login_route") {
            LoginScreen(
                onSignInSuccess = {
                    navController.navigate("dashboard_route") {
                        // This prevents the user from going back to the login screen
                        popUpTo("login_route") { inclusive = true }
                    }
                }
            )
        }
        // You'll add other screens here, e.g., dashboard
        composable(route = "dashboard_route") {
            DashboardScreen()
        }
    }
}