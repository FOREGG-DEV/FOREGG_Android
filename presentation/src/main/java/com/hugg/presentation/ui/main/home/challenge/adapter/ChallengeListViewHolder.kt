package com.hugg.presentation.ui.main.home.challenge.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugg.domain.model.response.ChallengeCardVo
import com.hugg.presentation.R
import com.hugg.presentation.databinding.ItemChallengeBinding

class ChallengeListViewHolder(
    private val binding: ItemChallengeBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: ChallengeCardVo) {
        binding.apply {
            challengeItemTitle.text = item.name
            challengeItemContainer.text = item.description
            challengeParticipantsText.text = context.getString(R.string.challenge_participant_count_text, item.participants)
            Glide.with(binding.root)
                .load(item.image)
                .into(challengeImage)
        }
    }
}