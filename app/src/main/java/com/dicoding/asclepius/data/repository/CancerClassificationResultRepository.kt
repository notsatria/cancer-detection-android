package com.dicoding.asclepius.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity
import com.dicoding.asclepius.data.local.room.CancerClassificationDao
import com.dicoding.asclepius.data.retrofit.ApiService
import com.dicoding.asclepius.utils.AppExecutors

class CancerClassificationResultRepository private constructor(
    private val dao: CancerClassificationDao,
    private val appExecutors: AppExecutors
) {
    fun insertResult(entity: CancerClassificationEntity) {
        appExecutors.diskIO.execute {
            dao.insertResult(entity)
        }
    }

    fun getAllResult(): LiveData<List<CancerClassificationEntity>> {
        return dao.getAllResult()
    }


    companion object {
        @Volatile
        private var INSTANCE: CancerClassificationResultRepository? = null

        fun getInstance(
            dao: CancerClassificationDao,
            appExecutors: AppExecutors
        ): CancerClassificationResultRepository {
            return INSTANCE ?: synchronized(this) {
                CancerClassificationResultRepository(dao, appExecutors)
                    .also { INSTANCE = it }
            }
        }
    }
}