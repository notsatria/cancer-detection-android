package com.dicoding.asclepius.helper

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.dicoding.asclepius.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier


class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResult: Int = 2,
    val modelName: String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var imageClassifer: ImageClassifier? = null
    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(maxResult)
            .setScoreThreshold(threshold)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)

        try {
            imageClassifer = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build(),
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        if (imageClassifer == null) {
            setupImageClassifier()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(imageUri)))

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(imageUri))
    }

    private fun toBitmap(imageUri: Uri): Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

    private fun getOrientationFromRotation(imageUri: Uri): ImageProcessingOptions.Orientation {
        val orientationColumn = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cursor: Cursor? = context.contentResolver.query(imageUri, orientationColumn, null, null, null)
        var orientation: Int = -1
        if (cursor != null && cursor.moveToFirst()) {
            orientation = cursor.getColumnIndex(orientationColumn[0])
            cursor.close()
        }

        return when (orientation) {
            90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }

    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(result: List<Classifications>?)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

}