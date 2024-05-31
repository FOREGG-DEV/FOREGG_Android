package com.foregg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.ItemHomeIntroductionBinding

class HomeIntroductionViewHolder(
    private val binding: ItemHomeIntroductionBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Int) {
        binding.apply {
            imageView.setImageResource(item)
        }
    }
}