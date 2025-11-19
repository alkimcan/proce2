package com.spordestek.app.viewmodels

import androidx.lifecycle.ViewModel
import com.spordestek.app.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Represents the current user session state throughout the app.
 */
data class SessionState(
    val user: User? = null,
    val authToken: String? = null
) {
    val isUserLoggedIn: Boolean get() = user != null && authToken != null
}

/**
 * Manages the user session state, including login, logout, and user data.
 * This ViewModel can be shared across multiple Composables.
 */
class SessionViewModel : ViewModel() {

    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    fun onLoginSuccess(user: User, token: String) {
        _sessionState.value = SessionState(user = user, authToken = token)
        // TODO: Save token to persistent storage (DataStore)
    }

    fun onLogout() {
        _sessionState.value = SessionState()
        // TODO: Clear token from persistent storage (DataStore)
    }

    // Functiın to check auth status on app start can be added here later
}
