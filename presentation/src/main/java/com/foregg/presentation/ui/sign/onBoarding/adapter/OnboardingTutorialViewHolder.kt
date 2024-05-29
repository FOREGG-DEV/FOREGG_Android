package com.foregg.presentation.ui.sign.onBoarding.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.foregg.presentation.databinding.IncludeItemTutorialBinding

class OnboardingTutorialViewHolder(
    private val binding: IncludeItemTutorialBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : OnboardingTutorialVo) {
        binding.apply {
            textTitle.text = item.title
            textContent.text = item.content
            imgTutorial.setBackgroundResource(item.img)
        }
    }
}