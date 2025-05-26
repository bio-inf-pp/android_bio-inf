package com.example.bioinf.model

data class IDResponse(
    val tcga_id: String,
    val ensembl_id: String,
    val gene_value: Double,
    val features: List<Double>,
    val prediction: Double
)