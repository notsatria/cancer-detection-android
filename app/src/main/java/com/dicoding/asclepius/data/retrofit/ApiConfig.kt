package com.dicoding.asclepius.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig() {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "a1b3052e1474485597eeb31de44e93ea"

        fun getApiService() : ApiService {

            val client = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}