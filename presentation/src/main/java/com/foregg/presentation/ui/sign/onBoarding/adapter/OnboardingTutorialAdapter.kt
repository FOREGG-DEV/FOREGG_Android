package com.foregg.presentation.ui.sign.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.presentation.databinding.IncludeItemTutorialBinding

class OnboardingTutorialAdapter : ListAdapter<String, RecyclerView.ViewHolder>(
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

class OnboardingTutorialDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}