package com.example.bioinf.model

data class IDResponse(
    val tcga_id: String,
    val ensembl_id: String,
    val gene_value: Double,
    val features: List<Double>,
    val prediction: Double, // вероятность класса
    val predicted_label: Int //  предсказанный класс 1 или 0
)