package com.foregg.presentation.ui.main.home.challenge.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemMyChallengeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.challenge.ChallengeViewModel
import javax.inject.Inject

class MyChallengeListViewHolder(
    private val binding: ItemMyChallengeBinding,
    private val context: Context,
    private val viewModel: ChallengeViewModel,
    private val dialog: CommonDialog
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MyChallengeListItemVo) {
        binding.apply {
            challengeItemTitle.text = item.name
            challengeItemContainer.text = item.description
            challengeParticipantsText.text = context.getString(R.string.challenge_participant_count_text, item.participants)
            btnDelete.setOnClickListener {
                dialog
                    .setTitle(R.string.challenge_stop)
                    .setPositiveButton(R.string.word_yes) {
                        viewModel.quitChallenge(item.id)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.word_no) {
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}