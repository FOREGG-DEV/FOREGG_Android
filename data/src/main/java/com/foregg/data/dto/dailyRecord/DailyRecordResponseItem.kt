package com.foregg.data.dto.dailyRecord

import com.foregg.data.base.DataDto
import com.foregg.domain.model.enums.DailyConditionType
import com.foregg.domain.model.enums.EmotionType
import com.google.gson.annotations.SerializedName

data class DailyRecordResponseItem(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("emotionType")
    val emotionType: EmotionType? = EmotionType.DEFAULT
): DataDto
