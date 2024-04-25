package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.model.ClassificationResult
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var classificationResult: ClassificationResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getDataFromIntent()
        showResults()

        binding.btnBack.setOnClickListener {
            finish()
        }
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

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_RESULT = "extra_result"
    }
}