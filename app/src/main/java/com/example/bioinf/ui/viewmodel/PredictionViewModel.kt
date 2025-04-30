package com.example.bioinf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.ApiService
import com.example.bioinf.PredictionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PredictionViewModel : ViewModel() {
    private val apiService = ApiService.create()

    private val _predictionResult = MutableStateFlow<Float?>(null)
    val predictionResult: StateFlow<Float?> = _predictionResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun makePrediction(features: List<Double>) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.predict(PredictionRequest(features))
                _predictionResult.value = response.prediction
            } catch (e: HttpException) {
                _errorMessage.value = "Ошибка сервера: ${e.response()?.errorBody()?.string()}"
            } catch (e: Exception) {
                _errorMessage.value = "Сетевая ошибка: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}