package com.foregg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.HomeAdCardType
import com.foregg.domain.model.vo.home.HomeAdCardVo
import com.foregg.presentation.databinding.ItemHomeIntroductionBinding

class HomeIntroductionViewHolder(
    private val binding: ItemHomeIntroductionBinding,
    private val listener : HomeIntroductionAdapter.HomeIntroductionDelegate
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var type : HomeAdCardType

    init {
        binding.imageView.setOnClickListener {
            listener.onClickCard(type)
        }
    }

    fun bind(item: HomeAdCardVo) {
        this.type = item.type
        binding.apply {
            imageView.setImageResource(item.image)
        }
    }
}