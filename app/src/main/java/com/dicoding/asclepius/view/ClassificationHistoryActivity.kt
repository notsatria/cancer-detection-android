package com.dicoding.asclepius.view

import ViewModelFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.ClassificationHistoryAdapter
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity
import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import com.dicoding.asclepius.databinding.ActivityClassificationHistoryBinding
import com.dicoding.asclepius.viewmodel.ClassificationHistoryViewModel
import com.dicoding.asclepius.viewmodel.DashboardViewModel

class ClassificationHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassificationHistoryBinding
    private lateinit var viewModel: ClassificationHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassificationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = getString(R.string.history)
            elevation = 0f
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        this@ClassificationHistoryActivity,
                        R.color.md_theme_light_background
                    )
                )
            )
        }
        initViewModel()

        viewModel.getAllResults().observe(this) {
            if (it.isEmpty()) {
                showEmpty(getString(R.string.no_history))
            } else {
                showHistoryList(it)
            }
        }
    }

    private fun initViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(context = this)
        viewModel = ViewModelProvider(this, factory)[ClassificationHistoryViewModel::class.java]
    }

    private fun showHistoryList(list: List<CancerClassificationEntity>) {
        val rvResult = binding.rvResult
        val adapter = ClassificationHistoryAdapter(list)
        rvResult.adapter = adapter
        rvResult.layoutManager = LinearLayoutManager(this)
    }

    private fun showEmpty(message: String) {
        binding.tvError.text = message
        binding.llError.visibility = View.VISIBLE
        binding.ivError.setImageResource(R.drawable.empty)
    }
}