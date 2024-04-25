package com.dicoding.asclepius.view

import ViewModelFactory
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.model.ClassificationResult
import com.dicoding.asclepius.viewmodel.DashboardViewModel
import com.dicoding.asclepius.viewmodel.ResultViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var classificationResult: ClassificationResult
    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getDataFromIntent()
        showResults()
        initViewModel()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            viewModel.insertResult(
                resultString = classificationResult.resultString,
                imageUri = classificationResult.imgUri
            )
            showToast(getString(R.string.save_result_success))
            moveToMainActivity()
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, DashboardActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun initViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(context = this)
        viewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]
    }

    @Suppress("DEPRECATION")
    private fun getDataFromIntent() {
        if (Build.VERSION.SDK_INT >= TIRAMISU) {
            classificationResult = intent.getParcelableExtra(EXTRA_RESULT)!!
        } else {
            classificationResult = intent.getSerializableExtra(EXTRA_RESULT) as ClassificationResult
        }
    }

    private fun showResults() {
        binding.resultImage.setImageURI(classificationResult.imgUri)
        binding.resultText.text = classificationResult.resultString
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_RESULT = "extra_result"
    }
}