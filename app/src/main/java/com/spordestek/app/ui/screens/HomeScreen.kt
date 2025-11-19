package com.spordestek.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spordestek.app.ui.navigation.Screen
import com.spordestek.app.viewmodels.SessionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, sessionState: SessionState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SporDestek") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController, 
                currentRoute = Screen.Home.route,
                sessionState = sessionState
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HeroSection() }
            item { StatsSection(navController, sessionState) } // Pass session state
            item { HowItWorksSection() }
            item { QuickActionsSection(navController, sessionState) } // Pass session state
        }
    }
}

// Other composables like HeroSection, HowItWorksSection remain the same...

@Composable
fun StatsSection(navController: NavController, sessionState: SessionState) {
    // ... existing implementation ...
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Toplam Puan",
            value = "0",
            icon = Icons.Default.Star,
            color = MaterialTheme.colorScheme.primary
        )
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Bugünkü Adım",
            value = "0",
            icon = Icons.Default.DirectionsWalk,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun QuickActionsSection(navController: NavController, sessionState: SessionState) {
     Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Hızlı İşlemler",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.DirectionsWalk,
                text = "Adım Kaydet",
                onClick = { 
                    if (sessionState.isUserLoggedIn) {
                        navController.navigate(Screen.Steps.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                }
            )
            QuickActionButton(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Favorite,
                text = "Kulüpler",
                onClick = { navController.navigate(Screen.Clubs.route) }
            )
        }
    }
}

// ... Other composables remain largely the same ...
@Composable
fun HeroSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF10B981),
                            Color(0xFF3B82F6)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Adımlarınızla Amatör Spor Kulüplerini Destekleyin",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Her gün attığınız adımları puana çevirin, sevdiğiniz kulüplere bağış yapın.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun HowItWorksSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nasıl Çalışır?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            HowItWorksStep(
                number = "1",
                title = "Adımlarınızı Kaydedin",
                description = "Günlük attığınız adımları uygulamaya girin."
            )
            HowItWorksStep(
                number = "2",
                title = "Puana Çevirin",
                description = "Her 1000 adım 1 puana eşittir."
            )
            HowItWorksStep(
                number = "3",
                title = "Kulüplere Bağış Yapın",
                description = "Kazandığınız puanları sevdiğiniz kulüplere bağışlayın."
            )
        }
    }
}

@Composable
fun HowItWorksStep(number: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun QuickActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String, sessionState: SessionState) {
    val items = listOf(
        Screen.Home, 
        Screen.Clubs, 
        Screen.Steps, 
        Screen.Leaderboard, 
        Screen.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            val (icon, label) = when (screen) {
                Screen.Home -> Icons.Default.Home to "Ana Sayfa"
                Screen.Clubs -> Icons.Default.Favorite to "Kulüpler"
                Screen.Steps -> Icons.Default.DirectionsWalk to "Adımlarım"
                Screen.Leaderboard -> Icons.Default.Leaderboard to "Sıralama"
                Screen.Profile -> Icons.Default.Person to "Profilim"
                else -> Icons.Default.Help to ""
            }
            
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    val route = if (screen in listOf(Screen.Profile, Screen.Steps) && !sessionState.isUserLoggedIn) {
                        Screen.Login.route
                    } else {
                        screen.route
                    }
                    navController.navigate(route)
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}
