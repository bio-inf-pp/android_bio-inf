package com.example.bioinf.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.ApiService
import com.example.bioinf.PredictionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PredictionViewModel : ViewModel() {
    private val apiService = ApiService.create()

    private val _predictionResult = MutableStateFlow<Float?>(null)
    val predictionResult: StateFlow<Float?> = _predictionResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun predictFromFile(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val features = context.assets.open("features.txt")
                    .bufferedReader()
                    .use { it.readText() }
                    .trim()
                    .split(",")
                    .map { it.trim().toDouble() }

                val request = PredictionRequest(features = features)
                val response = apiService.predict(request)

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