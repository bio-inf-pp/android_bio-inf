package com.example.bioinf.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.data.ApiService
import com.example.bioinf.model.PredictionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilePredictionViewModel(
    private val apiService: ApiService,
) : ViewModel() {

    private val _predictionResult = MutableStateFlow(PredictionResult())
    val predictionResult: StateFlow<PredictionResult> = _predictionResult

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

                _predictionResult.value = PredictionResult(
                    predictionResult = "Предсказанный класс вероятности рака - ${response.predicted_label}" + when (response.predicted_label) {
                        0 -> "\nРака нет"
                        1 -> "\nРак есть"
                        else -> "\nНеопозанный результат. Попробуйте снова"
                    },
                    predictionPresent = response.prediction
                )

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