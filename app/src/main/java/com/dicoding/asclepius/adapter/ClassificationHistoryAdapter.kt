package com.dicoding.asclepius.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.CancerClassificationEntity
import com.dicoding.asclepius.databinding.CardNewsItemBinding
import com.dicoding.asclepius.databinding.ClassificationResultItemBinding
import com.dicoding.asclepius.utils.DateUtil

class ClassificationHistoryAdapter(private val list: List<CancerClassificationEntity>) :
    RecyclerView.Adapter<ClassificationHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ClassificationResultItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassificationHistoryAdapter.ViewHolder {
        val binding =
            ClassificationResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ClassificationHistoryAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                val resolver = holder.itemView.context.contentResolver
                val uri = Uri.parse(imageUri)
                val bitmap = resolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)
                }
                binding.ivResult.setImageBitmap(bitmap)
                binding.tvResultTitle.text = "Hasil $id"
                binding.tvResultDesc.text = resultString
                binding.tvResultDate.text = DateUtil.formatNewsDate(createdAt)
            }
        }
    }
}