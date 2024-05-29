package com.foregg.presentation.ui.sign.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.foregg.presentation.databinding.IncludeItemTutorialBinding

class OnboardingTutorialAdapter : ListAdapter<OnboardingTutorialVo, RecyclerView.ViewHolder>(
    OnboardingTutorialDiffCallBack()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OnboardingTutorialViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingTutorialViewHolder(binding)
    }

}

class OnboardingTutorialDiffCallBack : DiffUtil.ItemCallback<OnboardingTutorialVo>() {
    override fun areItemsTheSame(oldItem: OnboardingTutorialVo, newItem: OnboardingTutorialVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: OnboardingTutorialVo, newItem: OnboardingTutorialVo): Boolean = oldItem == newItem
}