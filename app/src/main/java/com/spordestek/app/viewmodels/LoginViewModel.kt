package com.spordestek.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spordestek.app.data.api.RetrofitClient
import com.spordestek.app.data.auth.TokenManager
import com.spordestek.app.data.models.LoginRequest
import com.spordestek.app.data.models.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val response: LoginResponse) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val request = LoginRequest(email = email, password = password)
                val response = RetrofitClient.apiService.login(request)

                if (response.isSuccessful && response.body()?.data != null) {
                    val loginResponse = response.body()!!.data!!
                    // Token ve kullanıcı bilgilerini kaydet
                    TokenManager.saveToken(getApplication(), loginResponse.token)
                    TokenManager.saveUserInfo(
                        context = getApplication(),
                        userId = loginResponse.user.id,
                        name = loginResponse.user.name,
                        email = loginResponse.user.email,
                        role = loginResponse.user.role
                    )
                    _uiState.value = LoginUiState.Success(loginResponse)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Geçersiz e-posta veya şifre"
                    _uiState.value = LoginUiState.Error("Giriş başarısız: $errorBody")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Bir hata oluştu: ${e.message}")
            }
        }
    }
}
