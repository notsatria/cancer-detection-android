package com.dicoding.asclepius.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassificationResult(
    val imgUri: Uri,
    val resultString: String
) : Parcelable
