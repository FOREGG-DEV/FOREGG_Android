package com.foregg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.databinding.ItemHomeMyChallengeBinding

class HomeChallengeViewHolder(
    private val binding: ItemHomeMyChallengeBinding,
    private val listener: HomeChallengeAdapter.HomeChallengeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var itemId: Long? = null

    init {
        binding.btnCompleteChallenge.setOnClickListener {
            itemId?.let { id ->
                listener.showDialog(id)
            }
        }
    }

    fun bind(item: MyChallengeListItemVo) {
        itemId = item.id
        binding.apply {
            textChallengeName.text = item.name
        }
    }
}