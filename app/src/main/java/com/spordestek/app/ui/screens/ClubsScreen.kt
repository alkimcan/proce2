package com.spordestek.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.spordestek.app.data.models.Club
import com.spordestek.app.ui.navigation.Screen
import com.spordestek.app.viewmodels.ClubsUiState
import com.spordestek.app.viewmodels.ClubsViewModel
import com.spordestek.app.viewmodels.SessionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsScreen(
    navController: NavController,
    sessionState: SessionState, // sessionState eklendi
    clubsViewModel: ClubsViewModel = viewModel()
) {
    val uiState by clubsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kulüpler") },
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
                currentRoute = Screen.Clubs.route,
                sessionState = sessionState // sessionState iletildi
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ClubsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ClubsUiState.Success -> {
                    ClubsList(clubs = state.clubs, navController = navController)
                }
                is ClubsUiState.Error -> {
                    Text(text = state.message)
                }
            }
        }
    }
}

@Composable
fun ClubsList(clubs: List<Club>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Amatör Spor Kulüpleri",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(clubs) { club ->
            ClubItem(club = club, onClick = {
                navController.navigate(Screen.ClubDetail.createRoute(club.id))
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubItem(club: Club, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = club.logo,
                contentDescription = "${club.name} logo",
                modifier = Modifier.size(64.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = club.city ?: "Şehir belirtilmemiş",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailScreen(clubId: Int, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kulüp Detayı") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Kulüp ID: $clubId",
                style = MaterialTheme.typography.headlineSmall
            )
            // TODO: Kulüp detaylarını API'den çek ve göster
        }
    }
}
