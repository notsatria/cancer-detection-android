package com.dicoding.asclepius.data.local.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "classification_result")
@Parcelize
data class CancerClassificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "result_string")
    val resultString: String,

    @ColumnInfo(name = "image_uri")
    val imageUri: Uri,

    @ColumnInfo(name = "created_at")
    val createdAt: String,
) : Parcelable
