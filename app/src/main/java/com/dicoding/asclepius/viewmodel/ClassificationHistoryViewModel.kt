package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.CancerClassificationResultRepository

class ClassificationHistoryViewModel(private val repository: CancerClassificationResultRepository) : ViewModel() {
    fun getAllResults() = repository.getAllResult()
}