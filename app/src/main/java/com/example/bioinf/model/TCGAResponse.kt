package com.example.bioinf.model

data class TCGAResponse(
    val tcga_id: String,
    val features: List<Double>?,
    val prediction: Double, // вероятность класса
    val predicted_label: Int //  предсказанный класс 1 или 0
)
