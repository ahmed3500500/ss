package com.cryptosignals.app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("signals/latest")
    suspend fun getSignals(@Query("limit") limit: Int = 20): SignalResponse

    companion object {
        private const val BASE_URL = "http://37.49.228.169:8000/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
