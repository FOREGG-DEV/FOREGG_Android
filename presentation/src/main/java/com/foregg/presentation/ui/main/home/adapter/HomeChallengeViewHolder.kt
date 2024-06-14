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
    private var isSuccess : Boolean = false

    init {
        binding.btnCompleteChallenge.setOnClickListener {
            itemId?.let { id ->
                if(isSuccess) listener.deleteComplete(id) else listener.showDialog(id)
            }
        }
    }

    fun bind(item: MyChallengeListItemVo) {
        itemId = item.id
        binding.apply {
            textChallengeName.text = item.name
            isSuccess = if(item.successDays?.any { it == today } == true){
                btnCompleteChallenge.setImageResource(R.drawable.ic_btn_complete_challenge_already)
                true
            } else false

            Glide.with(binding.root)
                .load(item.image)
                .into(challengeImage)
        }
    }
}