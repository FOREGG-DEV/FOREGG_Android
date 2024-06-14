package com.foregg.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemHomeMyChallengeBinding
import com.foregg.presentation.util.TimeFormatter
import org.threeten.bp.LocalDate

class HomeChallengeViewHolder(
    private val binding: ItemHomeMyChallengeBinding,
    private val listener: HomeChallengeAdapter.HomeChallengeDelegate
) : RecyclerView.ViewHolder(binding.root) {
    private val today = TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek)
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
            item.successDays?.forEach { day ->
                if (day == today) {
                    btnCompleteChallenge.setImageResource(R.drawable.ic_btn_complete_challenge_already)
                    btnCompleteChallenge.isClickable = false
                }
            }
            Glide.with(binding.root)
                .load(item.image)
                .into(challengeImage)
        }
    }
}