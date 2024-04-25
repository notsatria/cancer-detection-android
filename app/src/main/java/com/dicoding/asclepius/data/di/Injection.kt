package com.dicoding.asclepius.data.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.CancerClassificationDao
import com.dicoding.asclepius.data.local.room.CancerClassificationDatabase
import com.dicoding.asclepius.data.repository.CancerClassificationResultRepository
import com.dicoding.asclepius.data.repository.HeadlineNewsRepository
import com.dicoding.asclepius.data.retrofit.ApiConfig
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun provideHeadlineNewsRepository(): HeadlineNewsRepository {
        val apiService = ApiConfig.getApiService()
        return HeadlineNewsRepository.getInstance(apiService)
    }

    fun provideCancerClassificationResultRepository(context: Context): CancerClassificationResultRepository {
        val database = CancerClassificationDatabase.getInstance(context)
        val dao = database.dao()
        val appExecutors = AppExecutors()

        return CancerClassificationResultRepository.getInstance(dao, appExecutors)
    }
}