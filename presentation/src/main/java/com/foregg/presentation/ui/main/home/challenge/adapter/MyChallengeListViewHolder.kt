package com.foregg.presentation.ui.main.home.challenge.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemMyChallengeBinding
import com.foregg.presentation.ui.main.home.challenge.ChallengeViewModel

class MyChallengeListViewHolder(
    private val binding: ItemMyChallengeBinding,
    private val context: Context,
    private val viewModel: ChallengeViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MyChallengeListItemVo) {
        binding.apply {
            challengeItemTitle.text = item.name
            challengeItemContainer.text = item.description
            challengeParticipantsText.text = context.getString(R.string.challenge_participant_count_text, item.participants)
            btnDelete.setOnClickListener { viewModel.quitChallenge(item.id) }
        }
    }
}