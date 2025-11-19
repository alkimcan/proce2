package com.spordestek.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spordestek.app.data.api.RetrofitClient
import com.spordestek.app.data.models.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Arayüzün durumunu temsil eden sealed interface
sealed interface ClubsUiState {
    object Loading : ClubsUiState
    data class Success(val clubs: List<Club>) : ClubsUiState
    data class Error(val message: String) : ClubsUiState
}

class ClubsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ClubsUiState>(ClubsUiState.Loading)
    val uiState: StateFlow<ClubsUiState> = _uiState

    init {
        fetchClubs()
    }

    private fun fetchClubs() {
        viewModelScope.launch {
            _uiState.value = ClubsUiState.Loading
            try {
                val response = RetrofitClient.apiService.getClubs()
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.data != null) {
                        _uiState.value = ClubsUiState.Success(apiResponse.data)
                    } else {
                        _uiState.value = ClubsUiState.Error(apiResponse?.message ?: "Boş veri")
                    }
                } else {
                    _uiState.value = ClubsUiState.Error("Kulüpler alınamadı: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = ClubsUiState.Error("Bir hata oluştu: ${e.message}")
            }
        }
    }
}
