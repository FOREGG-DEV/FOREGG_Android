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
    val binding: ItemDailyRecordBinding,
    private val listener: DailyRecordAdapter.DailyRecordDelegate
) : RecyclerView.ViewHolder(binding.root) {
    var id: Long? = null
    private var dailyRecordItem: DailyRecordResponseItemVo? = null

    init {
        binding.apply {
            btnCheck.setOnClickListener {
                id?.let {
                    listener.onClickEmotion(
                        PutEmotionVo(
                            it,
                            EmotionVo(EmotionType.CHECK)
                        )
                    )
                }
            }
            btnClap.setOnClickListener {
                id?.let {
                    listener.onClickEmotion(
                        PutEmotionVo(
                            it,
                            EmotionVo(EmotionType.CLAP)
                        )
                    )
                }
            }
            btnFingerHeart.setOnClickListener {
                id?.let {
                    listener.onClickEmotion(
                        PutEmotionVo(
                            it,
                            EmotionVo(EmotionType.FINGER_HEART)
                        )
                    )
                }
            }
            btnHeart.setOnClickListener {
                id?.let {
                    listener.onClickEmotion(
                        PutEmotionVo(
                            it,
                            EmotionVo(EmotionType.HEART)
                        )
                    )
                }
            }
            btnSad.setOnClickListener {
                id?.let {
                    listener.onClickEmotion(
                        PutEmotionVo(
                            it,
                            EmotionVo(EmotionType.SAD)
                        )
                    )
                }
            }
            if (UserInfo.info.genderType == GenderType.FEMALE) {
                dailyRecordLayout.setOnLongClickListener {
                    dailyRecordItem?.let {
                        listener.onClickDailyRecord(it)
                        dailyRecordLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_gs_10_radius_8)
                    }
                    return@setOnLongClickListener true
                }
            }
        }
    }

    fun bind(item: DailyRecordResponseItemVo) {
        val imageResource = setImageResource(item.dailyConditionType)
        id = item.id
        dailyRecordItem = item

        binding.apply {
            if(imageResource != -1) imageViewEmotion.setImageResource(imageResource)
            setImageResource(item.emotionType)
            imageViewLine.setImageResource(
                if (TimeFormatter.getToday() == item.date) R.drawable.ic_today_record_item_line
                else R.drawable.ic_record_item_line
            )
            textDate.text = item.date
            dailyRecordText.text = item.content
            if (UserInfo.info.genderType == GenderType.FEMALE) layoutBtnHusbandEmotion.visibility = View.GONE
            dailyRecordLayout.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        }
    }

    private fun setImageResource(type: DailyConditionType): Int {
        return when (type) {
            DailyConditionType.WORST -> R.drawable.ic_emotion_worst_selected
            DailyConditionType.BAD -> R.drawable.ic_emotion_bad_selected
            DailyConditionType.SOSO -> R.drawable.ic_emotion_soso_selected
            DailyConditionType.GOOD -> R.drawable.ic_emotion_smile_selected
            DailyConditionType.PERFECT -> R.drawable.ic_emotion_perfect_selected
            DailyConditionType.DEFAULT -> -1
        }
    }

    private fun setImageResource(type: EmotionType) {
        when (type) {
            EmotionType.CHECK -> selectCheck()
            EmotionType.CLAP -> selectClap()
            EmotionType.FINGER_HEART -> selectFingerHeart()
            EmotionType.HEART -> selectHeart()
            EmotionType.SAD -> selectSad()
            EmotionType.DEFAULT -> unSelectAll()
        }
    }

    private fun selectCheck(){
        binding.apply {
            imageViewEmotionHusband.setImageResource(R.drawable.ic_check_selected)
            btnCheck.setImageResource(R.drawable.ic_check_selected)
            btnClap.setImageResource(R.drawable.ic_clap_unselected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_unselected)
            btnHeart.setImageResource(R.drawable.ic_heart_unselected)
            btnSad.setImageResource(R.drawable.ic_sad_unselected)
        }
    }

    private fun selectClap(){
        binding.apply {
            imageViewEmotionHusband.setImageResource(R.drawable.ic_clap_selected)
            btnClap.setImageResource(R.drawable.ic_clap_selected)
            btnCheck.setImageResource(R.drawable.ic_check_unselected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_unselected)
            btnHeart.setImageResource(R.drawable.ic_heart_unselected)
            btnSad.setImageResource(R.drawable.ic_sad_unselected)
        }
    }

    private fun selectFingerHeart(){
        binding.apply {
            imageViewEmotionHusband.setImageResource(R.drawable.ic_finger_heart_selected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_selected)
            btnCheck.setImageResource(R.drawable.ic_check_unselected)
            btnClap.setImageResource(R.drawable.ic_clap_unselected)
            btnHeart.setImageResource(R.drawable.ic_heart_unselected)
            btnSad.setImageResource(R.drawable.ic_sad_unselected)
        }
    }

    private fun selectHeart(){
        binding.apply {
            imageViewEmotionHusband.setImageResource(R.drawable.ic_heart_selected)
            btnHeart.setImageResource(R.drawable.ic_heart_selected)
            btnCheck.setImageResource(R.drawable.ic_check_unselected)
            btnClap.setImageResource(R.drawable.ic_clap_unselected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_unselected)
            btnSad.setImageResource(R.drawable.ic_sad_unselected)
        }
    }

    private fun selectSad(){
        binding.apply {
            imageViewEmotionHusband.setImageResource(R.drawable.ic_sad_selected)
            btnSad.setImageResource(R.drawable.ic_sad_selected)
            btnCheck.setImageResource(R.drawable.ic_check_unselected)
            btnClap.setImageResource(R.drawable.ic_clap_unselected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_unselected)
            btnHeart.setImageResource(R.drawable.ic_heart_unselected)
        }
    }

    private fun unSelectAll(){
        binding.apply {
            imageViewEmotionHusband.visibility = View.GONE
            btnCheck.setImageResource(R.drawable.ic_check_unselected)
            btnClap.setImageResource(R.drawable.ic_clap_unselected)
            btnFingerHeart.setImageResource(R.drawable.ic_finger_heart_unselected)
            btnHeart.setImageResource(R.drawable.ic_heart_unselected)
            btnSad.setImageResource(R.drawable.ic_sad_unselected)
        }
    }
}