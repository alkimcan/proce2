package com.spordestek.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spordestek.app.ui.screens.*
import com.spordestek.app.viewmodels.SessionState

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Clubs : Screen("clubs")
    object ClubDetail : Screen("club_detail/{clubId}") {
        fun createRoute(clubId: Int) = "club_detail/$clubId"
    }
    object Steps : Screen("steps")
    object Leaderboard : Screen("leaderboard")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    sessionState: SessionState,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, sessionState = sessionState)
        }
        
        composable(Screen.Clubs.route) {
            ClubsScreen(navController = navController, sessionState = sessionState)
        }
        
        composable(Screen.ClubDetail.route) { backStackEntry ->
            val clubId = backStackEntry.arguments?.getString("clubId")?.toIntOrNull() ?: 0
            ClubDetailScreen(clubId = clubId, navController = navController)
        }
        
        composable(Screen.Steps.route) {
            StepsScreen(navController = navController, sessionState = sessionState)
        }
        
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(navController = navController, sessionState = sessionState)
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, sessionState = sessionState)
        }
        
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
    }
}
