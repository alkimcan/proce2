package com.spordestek.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.spordestek.app.ui.navigation.Screen
import com.spordestek.app.viewmodels.RegisterUiState
import com.spordestek.app.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    val uiState by registerViewModel.uiState.collectAsState()

    // Navigate on successful registration
    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            navController.navigate(Screen.Login.route) {
                // Clear back stack to prevent going back to register screen
                popUpTo(Screen.Login.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Üye Ol") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Yeni Hesap Oluştur",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Ad Soyad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-posta Adresi") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Şifre") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
                label = { Text("Şifre Tekrar") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState is RegisterUiState.Error) {
                Text(
                    text = (uiState as RegisterUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (uiState is RegisterUiState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { 
                        registerViewModel.registerUser(name, email, password, passwordConfirm)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Üye Ol")
                }
            }
        }
    }
}
