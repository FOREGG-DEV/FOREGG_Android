package com.foregg.presentation.ui.sign.onBoarding.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.IncludeItemTutorialBinding

class OnboardingTutorialViewHolder(
    private val binding: IncludeItemTutorialBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : String) {
        binding.apply {
            textContent.text = item
        }
    }
}