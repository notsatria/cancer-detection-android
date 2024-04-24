package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.HeadlineNewsRepository

class DashboardViewModel(private val repository: HeadlineNewsRepository) : ViewModel() {
    fun getHeadlineNews() = repository.getTopHeadlines()
}