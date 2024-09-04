package com.hugg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hugg.domain.model.enums.HomeAdCardType
import com.hugg.domain.model.vo.home.HomeAdCardVo
import com.hugg.presentation.databinding.ItemHomeIntroductionBinding

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