package com.spordestek.app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.spordestek.app.data.api.RetrofitClient
import com.spordestek.app.data.auth.TokenManager
import com.spordestek.app.data.models.LoginResponse
import com.spordestek.app.data.models.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RegisterUiState {
    object Idle : RegisterUiState
    object Loading : RegisterUiState
    data class Success(val response: LoginResponse) : RegisterUiState
    data class Error(val message: String) : RegisterUiState
}

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser(name: String, email: String, password: String, passwordConfirm: String) {
        if (password != passwordConfirm) {
            _uiState.value = RegisterUiState.Error("Şifreler eşleşmiyor.")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                val request = RegisterRequest(name = name, email = email, password = password)
                val response = RetrofitClient.apiService.register(request)

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
                    _uiState.value = RegisterUiState.Success(loginResponse)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Bilinmeyen bir sunucu hatası."
                    Log.e("RegisterViewModel", "Kayıt Hatası (Sunucudan Gelen):\n$errorBody")
                    _uiState.value = RegisterUiState.Error("Kayıt başarısız. Lütfen bilgileri kontrol edip tekrar deneyin.")
                }
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Kayıt Hatası (Uygulama Kaynaklı):", e)
                _uiState.value = RegisterUiState.Error("Bir ağ hatası oluştu. İnternet bağlantınızı kontrol edin.")
            }
        }
    }
}
