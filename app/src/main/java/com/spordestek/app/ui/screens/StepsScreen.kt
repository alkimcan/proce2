package com.spordestek.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spordestek.app.ui.navigation.Screen
import com.spordestek.app.viewmodels.SessionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsScreen(navController: NavController, sessionState: SessionState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adımlarım") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController, 
                currentRoute = Screen.Steps.route,
                sessionState = sessionState
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsWalk,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Bugünkü Adım Sayısı",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Button(
                onClick = { /* TODO: Sync with Google Fit */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Sync, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Google Fit'ten Senkronize Et")
            }
            
            // TODO: Add history section
        }
    }
}
