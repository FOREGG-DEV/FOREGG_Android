package com.foregg.presentation.ui.dailyRecord.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemSideEffectBinding
import com.foregg.presentation.util.TimeFormatter

class SideEffectViewHolder(
    private val binding: ItemSideEffectBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SideEffectListItemVo) {
        binding.apply {
            imageViewLine.setImageResource(
                if (TimeFormatter.getToday() == item.date) R.drawable.ic_today_record_item_line
                else R.drawable.ic_record_item_line
            )
            textDate.text = item.date
            dailyRecordText.text = item.content
        }
    }
}