package com.foregg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.databinding.ItemHomeMyChallengeBinding

class HomeChallengeViewHolder(
    private val binding: ItemHomeMyChallengeBinding,
    private val listener: HomeChallengeAdapter.HomeChallengeDelegate
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MyChallengeListItemVo) {
        binding.apply {
            textChallengeName.text = item.name
            btnCompleteChallenge.setOnClickListener {
                listener.showDialog(item.id)
            }
        }
    }
}