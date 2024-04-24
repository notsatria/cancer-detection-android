package com.dicoding.asclepius.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import com.dicoding.asclepius.data.retrofit.ApiService
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeadlineNewsRepository private constructor(
    private val apiService: ApiService
) {
    private val result = MediatorLiveData<Result<HealthCancerNewsResponse>>()

    fun getTopHeadlines(): LiveData<Result<HealthCancerNewsResponse>> {
        result.value = Result.Loading
        val client =
            apiService.getTopHeadlines(
                "cancer",
                "health",
                "en",
                ApiConfig.API_KEY
            )

        client.enqueue(object : Callback<HealthCancerNewsResponse> {
            override fun onResponse(
                call: Call<HealthCancerNewsResponse>,
                response: Response<HealthCancerNewsResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.totalResults == null) {
                        result.value = Result.Empty("No data found")
                    }
                    result.value = response.body()?.let { Result.Success(it) }
                } else {
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<HealthCancerNewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })

        return result
    }

    companion object {
        @Volatile
        private var INSTANCE: HeadlineNewsRepository? = null

        fun getInstance(apiService: ApiService): HeadlineNewsRepository {
            return INSTANCE ?: synchronized(this) {
                HeadlineNewsRepository(apiService).also { INSTANCE = it }
            }
        }
    }
}