package com.spordestek.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
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
fun LeaderboardScreen(navController: NavController, sessionState: SessionState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sıralama") },
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
                currentRoute = Screen.Leaderboard.route,
                sessionState = sessionState
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Liderlik Tablosu",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // TODO: API'den liderlik tablosunu çek ve listele
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Sıralama yükleniyor...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
