package com.dicoding.asclepius.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity
import com.dicoding.asclepius.data.repository.CancerClassificationResultRepository
import com.dicoding.asclepius.utils.DateUtil

class ResultViewModel(private val repository: CancerClassificationResultRepository) : ViewModel() {
    fun insertResult(resultString: String, imageUri: Uri) {
        val entity = CancerClassificationEntity(
            resultString = resultString,
            imageUri = imageUri.toString(),
            createdAt = DateUtil.getCurrentDate()
        )
        repository.insertResult(entity)
    }

    fun getAllResults() = repository.getAllResult()
}