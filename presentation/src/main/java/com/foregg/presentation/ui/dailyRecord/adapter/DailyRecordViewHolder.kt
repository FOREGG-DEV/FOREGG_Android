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
            when(item.dailyConditionType) {
                DailyConditionType.WORST -> imageViewEmotion.setImageResource(R.drawable.ic_emotion_worst_selected)
                DailyConditionType.BAD -> imageViewEmotion.setImageResource(R.drawable.ic_emotion_bad_selected)
                DailyConditionType.SOSO -> imageViewEmotion.setImageResource(R.drawable.ic_emotion_soso_selected)
                DailyConditionType.GOOD -> imageViewEmotion.setImageResource(R.drawable.ic_emotion_smile_selected)
                DailyConditionType.PERFECT -> imageViewEmotion.setImageResource(R.drawable.ic_emotion_perfect_selected)
                DailyConditionType.DEFAULT -> imageViewEmotion.visibility = View.GONE
            }
            when(item.emotionType) {
                EmotionType.HEART -> imageViewEmotionHusband.setImageResource(R.drawable.ic_heart)
                EmotionType.THUMBS_UP -> imageViewEmotionHusband.setImageResource(R.drawable.ic_thumbs_up)
                EmotionType.CLAP -> imageViewEmotionHusband.setImageResource(R.drawable.ic_clap)
                EmotionType.SAD -> imageViewEmotionHusband.setImageResource(R.drawable.ic_emotion_sad_selected)
                EmotionType.SMILE -> imageViewEmotionHusband.setImageResource(R.drawable.ic_emotion_perfect_selected)
                EmotionType.DEFAULT -> imageViewEmotionHusband.visibility = View.GONE
            }
            if (TimeFormatter.getToday() == item.date) {
                imageViewLine.setImageResource(R.drawable.ic_today_record_item_line)
            } else imageViewLine.setImageResource(R.drawable.ic_record_item_line)
            textDate.text = item.date
            dailyRecordText.text = item.content
        }
    }
}