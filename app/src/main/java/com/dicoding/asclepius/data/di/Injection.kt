package com.dicoding.asclepius.data.di

import com.dicoding.asclepius.data.repository.HeadlineNewsRepository
import com.dicoding.asclepius.data.retrofit.ApiConfig

object Injection {
    fun provideHeadlineNewsRepository(): HeadlineNewsRepository {
        val apiService = ApiConfig.getApiService()
        return HeadlineNewsRepository.getInstance(apiService)
    }
}