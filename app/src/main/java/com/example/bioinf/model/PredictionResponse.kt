package com.example.bioinf.model

data class PredictionResponse(
    val prediction: Double, // вероятность класса
    val predicted_label: Int //  предсказанный класс 1 или 0
)