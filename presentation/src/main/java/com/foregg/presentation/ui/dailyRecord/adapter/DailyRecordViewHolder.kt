package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.EmotionType
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemDailyRecordBinding
import com.foregg.presentation.util.TimeFormatter

class DailyRecordViewHolder(
    private val binding: ItemDailyRecordBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: DailyRecordResponseItemVo) {
        binding.apply {
            val imageResource = setImageResource(item.dailyConditionType)
            val husbandImageResource = setImageResource(item.emotionType)

            imageViewEmotion.setImageResource(imageResource)
            imageViewEmotionHusband.setImageResource(husbandImageResource)

            if (TimeFormatter.getToday() == item.date) { imageViewLine.setImageResource(R.drawable.ic_today_record_item_line) }
            else imageViewLine.setImageResource(R.drawable.ic_record_item_line)

            textDate.text = item.date
            dailyRecordText.text = item.content
        }
    }

    private fun setImageResource(type: DailyConditionType): Int {
        return when(type) {
            DailyConditionType.WORST -> R.drawable.ic_emotion_worst_selected
            DailyConditionType.BAD -> R.drawable.ic_emotion_bad_selected
            DailyConditionType.SOSO -> R.drawable.ic_emotion_soso_selected
            DailyConditionType.GOOD -> R.drawable.ic_emotion_smile_selected
            DailyConditionType.PERFECT -> R.drawable.ic_emotion_perfect_selected
            DailyConditionType.DEFAULT -> View.GONE
        }
    }

    private fun setImageResource(type: EmotionType): Int {
        return when(type) {
            EmotionType.HEART -> R.drawable.ic_heart
            EmotionType.THUMBS_UP -> R.drawable.ic_thumbs_up
            EmotionType.CLAP -> R.drawable.ic_clap
            EmotionType.SAD -> R.drawable.ic_emotion_sad_selected
            EmotionType.SMILE -> R.drawable.ic_emotion_perfect_selected
            EmotionType.DEFAULT -> View.GONE
        }
    }
}