package com.example.bioinf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioinf.data.ApiService
import com.example.bioinf.model.TCGARequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PredictionResult(
   val predictionResult: String? = null,
    val predictionPresent: Double? = null
)

class TCGAViewModel : ViewModel() {
    private val apiService = ApiService.create()

    private val _predictionResult = MutableStateFlow<PredictionResult>(PredictionResult())
    val predictionResult: StateFlow<PredictionResult> = _predictionResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun predictById(tcgaId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.predictTsgaById(TCGARequest(tcgaId))
                _predictionResult.value = PredictionResult(
                    predictionResult =  "Предсказанный класс вероятности рака - ${response.predicted_label}" +   when(response.predicted_label){
                        0 -> "\nРака нет"
                        1 -> "\nРака есть"
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
