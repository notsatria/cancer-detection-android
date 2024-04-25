package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.response.HealthCancerNewsResponse
import com.dicoding.asclepius.databinding.CardNewsItemBinding
import com.dicoding.asclepius.utils.Formatter

class HeadlineNewsAdapter(private val list: List<HealthCancerNewsResponse.ArticlesItem>) :
    RecyclerView.Adapter<HeadlineNewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardNewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeadlineNewsAdapter.ViewHolder {
        val binding =
            CardNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HeadlineNewsAdapter.ViewHolder,
        position: Int
    ) {
        with(holder) {
            with(list[position]) {
                binding.tvNewsTitle.text = title
                binding.tvNewsDescription.text = description
                binding.tvNewsDate.text = Formatter.formatNewsDate(publishedAt!!)
                Glide.with(holder.itemView.context)
                    .load(urlToImage)
                    .into(binding.ivNews)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}