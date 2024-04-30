package com.foregg.presentation.ui.common.spinner

import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.IncludeItemSurgeryTypeBinding

class CommonSpinnerViewHolder(
    private val binding: IncludeItemSurgeryTypeBinding,
    private val listener : CommonSpinnerAdapter.CommonSpinnerDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var type: String

    init {
        binding.apply {
            constraintLayoutType.setOnClickListener {
                listener.onClickType(type)
            }
        }
    }

    fun bind(item : String) {
        type = item
        binding.apply {
            textType.text = item
        }
    }
}