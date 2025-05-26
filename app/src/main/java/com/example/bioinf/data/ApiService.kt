package com.example.bioinf.data

import com.example.bioinf.model.IDRequest
import com.example.bioinf.model.IDResponse
import com.example.bioinf.model.PredictionRequest
import com.example.bioinf.model.PredictionResponse
import com.example.bioinf.model.TCGARequest
import com.example.bioinf.model.TCGAResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("predict")
    suspend fun predict(@Body request: PredictionRequest): PredictionResponse

    @POST("predict_by_id")
    suspend fun predictById(@Body request: IDRequest): IDResponse

    @POST("predict_by_tcga")
    suspend fun predictTcgaById(@Body request: TCGARequest): TCGAResponse

    companion object {
        fun create(baseUrl: String): ApiService {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}
