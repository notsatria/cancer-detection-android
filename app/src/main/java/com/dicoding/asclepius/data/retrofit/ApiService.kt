package com.dicoding.asclepius.data.retrofit

import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Call<HealthCancerNewsResponse>
}