package com.foregg.presentation.ui.dailyRecord.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.EmotionType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.request.dailyRecord.EmotionVo
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo
import com.foregg.presentation.R
import com.foregg.presentation.databinding.ItemDailyRecordBinding
import com.foregg.presentation.util.TimeFormatter
import com.foregg.presentation.util.UserInfo

class DailyRecordViewHolder(
    private val binding: ItemDailyRecordBinding,
    private val listener: DailyRecordAdapter.DailyRecordDelegate
) : RecyclerView.ViewHolder(binding.root) {
    var id: Long? = null
    var dailyRecordItem: DailyRecordResponseItemVo? = null
    init {
        binding.apply {
            btnHeart.setOnClickListener { id?.let { listener.onClickEmotion(PutEmotionVo(it, EmotionVo(EmotionType.HEART))) } }
            btnThumbsUp.setOnClickListener { id?.let { listener.onClickEmotion(PutEmotionVo(it, EmotionVo(EmotionType.THUMBS_UP))) } }
            btnClap.setOnClickListener { id?.let { listener.onClickEmotion(PutEmotionVo(it, EmotionVo(EmotionType.CLAP))) } }
            btnSad.setOnClickListener { id?.let { listener.onClickEmotion(PutEmotionVo(it, EmotionVo(EmotionType.SAD))) } }
            btnPerfect.setOnClickListener { id?.let { listener.onClickEmotion(PutEmotionVo(it, EmotionVo(EmotionType.SMILE))) } }
            if (UserInfo.info.genderType == GenderType.FEMALE) dailyRecordLayout.setOnClickListener { dailyRecordItem?.let { listener.onClickDailyRecord(it) } }
        }
    }

    fun bind(item: DailyRecordResponseItemVo) {
        val imageResource = setImageResource(item.dailyConditionType)
        val husbandImageResource = setImageResource(item.emotionType)
        id = item.id
        dailyRecordItem = item

        binding.apply {

            imageViewEmotion.setImageResource(imageResource)
            if (husbandImageResource != -1) imageViewEmotionHusband.setImageResource(husbandImageResource)
            else imageViewEmotionHusband.visibility = View.GONE

            if (TimeFormatter.getToday() == item.date) { imageViewLine.setImageResource(R.drawable.ic_today_record_item_line) }
            else imageViewLine.setImageResource(R.drawable.ic_record_item_line)

            textDate.text = item.date
            dailyRecordText.text = item.content

            if (UserInfo.info.genderType == GenderType.FEMALE) layoutBtnHusbandEmotion.visibility = View.GONE
        }
    }

    private fun setImageResource(type: DailyConditionType): Int {
        return when(type) {
            DailyConditionType.WORST -> R.drawable.ic_emotion_worst_selected
            DailyConditionType.BAD -> R.drawable.ic_emotion_bad_selected
            DailyConditionType.SOSO -> R.drawable.ic_emotion_soso_selected
            DailyConditionType.GOOD -> R.drawable.ic_emotion_smile_selected
            DailyConditionType.PERFECT -> R.drawable.ic_emotion_perfect_selected
            DailyConditionType.DEFAULT -> -1
        }
    }

    private fun setImageResource(type: EmotionType): Int {
        return when(type) {
            EmotionType.HEART -> R.drawable.ic_heart
            EmotionType.THUMBS_UP -> R.drawable.ic_thumbs_up
            EmotionType.CLAP -> R.drawable.ic_clap
            EmotionType.SAD -> R.drawable.ic_emotion_sad_selected
            EmotionType.SMILE -> R.drawable.ic_emotion_perfect_selected
            EmotionType.DEFAULT -> -1
        }
    }
}