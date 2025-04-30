package com.example.bioinf

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("predict")
    suspend fun predict(@Body request: PredictionRequest): PredictionResponse

    @Multipart
    @POST("predict_from_file")
    suspend fun predictFromFile(
        @Part file: MultipartBody.Part
    ): PredictionResponse

    companion object {
        private const val BASE_URL = "https://ea9e-35-194-70-112.ngrok-free.app/"

        fun create(): ApiService {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

data class PredictionRequest(val features: List<Double>)
data class PredictionResponse(val prediction: Float)