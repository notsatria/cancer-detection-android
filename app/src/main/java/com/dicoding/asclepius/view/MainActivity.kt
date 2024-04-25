package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.model.ClassificationResult
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast(getString(R.string.image_not_found))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        initUI()
    }

    private fun initUI() {
        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        binding.previewImageView.setImageURI(currentImageUri)
    }

    private fun analyzeImage() {
        showLoading(true)
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        showLoading(false)
                        showToast(error)
                    }
                }

                override fun onResults(result: List<Classifications>?) {
                    showLoading(false)
                    Log.d(TAG, "onResults: $result")
                    val sortedCategories = result!![0].categories.sortedByDescending { it.score }
                    val resultString = sortedCategories.joinToString("\n") {
                        "${it.label} " + NumberFormat.getPercentInstance().format(it.score)
                    }
                    baseContext.contentResolver.takePersistableUriPermission(
                        currentImageUri!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    val classificationResult = ClassificationResult(currentImageUri!!, resultString)
                    moveToResult(classificationResult)
                }
            }
        )

        if (currentImageUri == null) {
            showLoading(false)
            showToast(getString(R.string.image_not_found))
            return
        }

        imageClassifierHelper.classifyStaticImage(currentImageUri!!)

    }

    private fun moveToResult(classificationResult: ClassificationResult) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_RESULT, classificationResult)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}