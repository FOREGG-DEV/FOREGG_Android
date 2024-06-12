package com.foregg.presentation.ui.dailyRecord.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.presentation.databinding.ItemSideEffectBinding

class SideEffectViewHolder(
    private val binding: ItemSideEffectBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SideEffectListItemVo) {
        binding.apply {
            textDate.text = item.date
            dailyRecordText.text = item.content
        }
    }
}