package com.harishri.financepal.presentation.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.harishri.financepal.R // Make sure this R file is in your project

@Composable
fun LoginScreen(
    onSignInSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    // We'll add the ViewModel and logic here in Step 4
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Observe the ViewModel's state and act on changes
    LaunchedEffect(key1 = uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onSignInSuccess()
        }
    }



    //TODO Enhancements for v2.0 to credential manager
    // Set up the Google Sign-In options
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(R.string.default_web_client_id))
        .requestEmail()
        .requestId()
        .requestProfile()
        .build()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken
                if (idToken != null) {
                    // Pass the ID token to the ViewModel for processing
                    viewModel.handleSignInResult(idToken, account)
                }
            } catch (e: ApiException) {
                // Pass the error to the ViewModel for handling
                viewModel.handleSignInResultError(e)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome Back!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val signInIntent = GoogleSignIn.getClient(context, gso).signInIntent
            googleSignInLauncher.launch(signInIntent)
        }) {
            Text("Sign in with Google")
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}