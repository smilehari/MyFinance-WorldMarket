package com.harishri.financepal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harishri.financepal.presentation.ui.theme.MyFinanceAndWorldMarketTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.harishri.financepal.presentation.nav.AppNavGraph
import com.harishri.financepal.presentation.ui.login.LoginViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFinanceAndWorldMarketTheme {
                // Obtain a reference to the ViewModel using Hilt.
                val loginViewModel: LoginViewModel = hiltViewModel()

                val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Show a loading indicator while the initial authentication check is in progress.
                    if (!uiState.isInitialCheckDone) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        // Once the check is done, decide the starting destination.
                        val startDestination = if (uiState.isLoggedIn) {
                            "dashboard_route"
                        } else {
                            "login_route"
                        }

                        // Pass the determined start destination to the navigation graph.
                        AppNavGraph(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }

            }
        }
    }
}
