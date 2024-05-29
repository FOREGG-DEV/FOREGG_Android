package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.databinding.ItemInfoBinding

class InformationViewHolder(
    private val binding: ItemInfoBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: InfoItemVo) {
        binding.apply {
            infoTitle.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .transform(CircleCrop())
                .into(btnInfo)
        }
    }

}