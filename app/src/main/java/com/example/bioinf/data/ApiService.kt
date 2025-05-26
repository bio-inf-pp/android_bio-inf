package com.example.bioinf.data

import com.example.bioinf.model.IDRequest
import com.example.bioinf.model.IDResponse
import com.example.bioinf.model.PredictionRequest
import com.example.bioinf.model.PredictionResponse
import com.example.bioinf.model.TCGARequest
import com.example.bioinf.model.TCGAResponse
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

    @POST("predict_by_id")
    suspend fun predictById(@Body request: IDRequest): IDResponse

    @POST("predict_by_id")
    suspend fun predictTsgaById(@Body request: TCGARequest): TCGAResponse

    companion object {
        private const val BASE_URL = "https://060f-34-73-147-116.ngrok-free.app"

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
