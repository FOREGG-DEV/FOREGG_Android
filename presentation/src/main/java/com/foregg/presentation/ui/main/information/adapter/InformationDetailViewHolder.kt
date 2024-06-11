package com.foregg.presentation.ui.main.information.adapter

import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding
import com.foregg.presentation.databinding.ItemInfoCategoryBinding
import com.foregg.presentation.databinding.ItemInfoCategoryDetailBinding

class InformationDetailViewHolder(
    private val binding: ItemInfoCategoryBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: InfoItemVo) {
        binding.apply {
            infoTitle.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .into(btnInfo)
        }
    }
}

class InformationCategoryDetailViewHolder(
    private val binding: ItemInfoCategoryDetailBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: InfoItemVo) {
        binding.apply {
            infoTitle.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .into(btnInfo)
        }
    }
}