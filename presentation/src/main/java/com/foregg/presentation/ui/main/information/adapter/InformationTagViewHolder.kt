package com.foregg.presentation.ui.main.information.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.R
import com.foregg.presentation.databinding.IncludeItemInformationTagBinding

class InformationTagViewHolder(
    private val binding: IncludeItemInformationTagBinding,
    private val listener: InformationTagAdapter.InformationTagDelegate
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.textTag.setOnClickListener {
            listener.onClick()
        }
    }

    fun bind(item: String, isDetail : Boolean) {
        binding.apply {
            textTag.setTextAppearance(
                if(isDetail) R.style.h1 else R.style.h2
            )
            textTag.text = item
        }
    }
}