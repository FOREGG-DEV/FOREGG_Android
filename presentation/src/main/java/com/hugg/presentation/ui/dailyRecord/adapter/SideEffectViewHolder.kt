package com.hugg.presentation.ui.dailyRecord.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.response.SideEffectListItemVo
import com.hugg.presentation.R
import com.hugg.presentation.databinding.ItemSideEffectBinding
import com.hugg.presentation.util.TimeFormatter
import com.hugg.presentation.util.UserInfo

class SideEffectViewHolder(
    val binding: ItemSideEffectBinding,
    private val listener : SideEffectAdapter.SideEffectDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var item: SideEffectListItemVo? = null

    init {
        binding.apply {
            if (UserInfo.info.genderType == GenderType.FEMALE) {
                layoutSideEffect.setOnLongClickListener {
                    item?.let {
                        listener.onLongClickSideEffect(it)
                        layoutSideEffect.setBackgroundResource(R.drawable.bg_rectangle_filled_gs_10_radius_8)
                    }
                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun bind(item: SideEffectListItemVo) {
        this.item = item
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