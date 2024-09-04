package com.hugg.presentation.ui.main.calendar.createOrEdit.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.hugg.domain.model.vo.SideEffectVo
import com.hugg.presentation.databinding.ItemSideEffectNoBarsBinding

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