package com.foregg.presentation.ui.main.calendar.createOrEdit.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.SideEffectVo
import com.foregg.presentation.databinding.ItemSideEffectNoBarsBinding

class SideEffectViewHolder(
    private val binding: ItemSideEffectNoBarsBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : SideEffectVo) {
        binding.apply {
            textDate.text = item.dateAndTime
            textSideEffect.text = item.content
        }
    }
}