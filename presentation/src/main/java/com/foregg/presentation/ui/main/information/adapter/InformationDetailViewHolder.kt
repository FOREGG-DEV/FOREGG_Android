package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoCategoryBinding
import com.foregg.presentation.databinding.ItemInfoCategoryDetailBinding

class InformationDetailViewHolder(
    private val binding: ItemInfoCategoryBinding,
    private val listener: InformationDetailAdapter.InformationDetailAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: InfoItemVo) {
        binding.apply {
            Glide.with(itemView.context)
                .load(item.image)
                .into(imgDetail)
        }
    }
}

class InformationCategoryDetailViewHolder(
    private val binding: ItemInfoCategoryDetailBinding,
    private val listener: InformationDetailAdapter.InformationDetailAdapterDelegate
): RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: InfoItemVo) {
        binding.apply {
            Glide.with(itemView.context)
                .load(item.image)
                .into(imgDetail)
        }
    }
}