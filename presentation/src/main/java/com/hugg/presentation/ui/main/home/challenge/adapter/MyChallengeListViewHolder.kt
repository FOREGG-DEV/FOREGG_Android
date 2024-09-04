package com.hugg.presentation.ui.main.home.challenge.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugg.domain.model.response.MyChallengeListItemVo
import com.hugg.presentation.R
import com.hugg.presentation.databinding.ItemMyChallengeBinding

class MyChallengeListViewHolder(
    private val binding: ItemMyChallengeBinding,
    private val context: Context,
    private val listener: MyChallengeListAdapter.DeleteMyChallengeDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var itemId: Long? = null

    init {
        binding.btnDelete.setOnClickListener {
            itemId?.let { id ->
                listener.showDialog(id)
            }
        }
    }

    fun bind(item: MyChallengeListItemVo) {
        itemId = item.id
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