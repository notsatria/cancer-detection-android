package com.dicoding.asclepius.data.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import com.dicoding.asclepius.data.retrofit.ApiService
import com.dicoding.asclepius.data.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeadlineNewsRepository private constructor(
    private val apiService: ApiService
) {
    private val result = MediatorLiveData<Result<HealthCancerNewsResponse>>()

    fun getTopHeadlines() {
        result.value = Result.Loading
        val client =
            apiService.getTopHeadlines(
                "cancer",
                "health",
                "en",
                "a1b3052e1474485597eeb31de44e93ea"
            )

        client.enqueue(object : Callback<HealthCancerNewsResponse> {
            override fun onResponse(
                call: Call<HealthCancerNewsResponse>,
                response: Response<HealthCancerNewsResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("HeadlineNewsRepository", "onResponse: ${response.body()}");
                } else {
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<HealthCancerNewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
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