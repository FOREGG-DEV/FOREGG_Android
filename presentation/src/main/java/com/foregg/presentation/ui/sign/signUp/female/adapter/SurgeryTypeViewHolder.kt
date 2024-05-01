package com.foregg.presentation.ui.sign.signUp.female.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.SurgeryType
import com.foregg.presentation.databinding.IncludeItemSurgeryTypeBinding

class SurgeryTypeViewHolder(
    private val binding: IncludeItemSurgeryTypeBinding,
    private val listener : SurgeryTypeAdapter.SurgeryTypeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var type: SurgeryType

    init {
        binding.apply {
            constraintLayoutType.setOnClickListener {
                listener.onClickType(type)
            }
        }
    }

    fun bind(item : SurgeryType) {
        type = item
        binding.apply {
            textType.text = item.type
        }
    }
}