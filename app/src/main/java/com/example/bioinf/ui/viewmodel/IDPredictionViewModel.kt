package com.example.bioinf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.data.ApiService
import com.example.bioinf.model.IDRequest
import com.example.bioinf.model.IDResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IDPredictionViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _predictionResult = MutableStateFlow<IDResponse?>(null)
    val predictionResult: StateFlow<IDResponse?> = _predictionResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun predictById(tcgaId: String, ensemblId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.predictById(IDRequest(tcgaId, ensemblId))
                _predictionResult.value = response
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
