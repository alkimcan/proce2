package com.spordestek.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.spordestek.app.ui.navigation.AppNavigation
import com.spordestek.app.ui.navigation.Screen
import com.spordestek.app.ui.theme.SporDestekTheme
import com.spordestek.app.viewmodels.LoginUiState
import com.spordestek.app.viewmodels.LoginViewModel
import com.spordestek.app.viewmodels.SessionViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SporDestekTheme {
                val navController = rememberNavController()
                val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()
                val sessionState by sessionViewModel.sessionState.collectAsStateWithLifecycle()

                // Başarılı girişten sonra otomatik yönlendirme ve oturum güncelleme
                LaunchedEffect(key1 = loginState) {
                    if (loginState is LoginUiState.Success) {
                        val loginResponse = (loginState as LoginUiState.Success).response
                        sessionViewModel.onLoginSuccess(loginResponse.user, loginResponse.token)
                        
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        navController = navController,
                        sessionState = sessionState
                    )
                }
            }
        }
    }
}
