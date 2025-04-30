package com.example.bioinf

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("predict")
    suspend fun predict(@Body request: PredictionRequest): PredictionResponse

    companion object {
        private const val BASE_URL = "https://b991-34-75-246-147.ngrok-free.app"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

data class PredictionRequest(val features: List<Double>)
data class PredictionResponse(val prediction: Float)