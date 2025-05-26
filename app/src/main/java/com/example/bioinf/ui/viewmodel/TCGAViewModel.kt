package com.example.bioinf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.data.ApiService
import com.example.bioinf.model.TCGARequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TCGAViewModel : ViewModel() {
    private val apiService = ApiService.create()

    private val _predictionResult = MutableStateFlow<Double?>(null)
    val predictionResult: StateFlow<Double?> = _predictionResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun predictById(tcgaId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.predictTsgaById(TCGARequest(tcgaId))
                _predictionResult.value = response.prediction
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
