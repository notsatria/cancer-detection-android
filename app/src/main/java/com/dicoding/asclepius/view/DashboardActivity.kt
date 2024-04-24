package com.dicoding.asclepius.view

import ViewModelFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HeadlineNewsAdapter
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import com.dicoding.asclepius.databinding.ActivityDashboardBinding
import com.dicoding.asclepius.viewmodel.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        getHeadlineNews()
    }

    private fun initViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
    }

    private fun getHeadlineNews() {
        viewModel.getHeadlineNews().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        @Suppress("UNCHECKED_CAST")
                        showHeadlineNews(result.data.articles as List<HealthCancerNewsResponse.ArticlesItem>)
                    }
                    is Result.Error -> showError(result, result.error)
                    is Result.Empty -> showError(result, result.emptyError)
                    else -> {
                        showError(Result.Empty(getString(R.string.something_went_wrong)), getString(R.string.something_went_wrong))
                    }
                }
            }
        }
    }

    private fun showHeadlineNews(list: List<HealthCancerNewsResponse.ArticlesItem>) {
        val rvNews = binding.rvHeadlineNews
        val adapter = HeadlineNewsAdapter(list)
        rvNews.adapter = adapter
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.setHasFixedSize(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(result: Result<HealthCancerNewsResponse>, message: String) {
        binding.tvError.text = message
        binding.llError.visibility = View.VISIBLE
        if (result is Result.Empty) {
            binding.ivError.setImageResource(R.drawable.empty)
        } else if (result is Result.Error) {
            binding.ivError.setImageResource(R.drawable.error)
        }
    }
}