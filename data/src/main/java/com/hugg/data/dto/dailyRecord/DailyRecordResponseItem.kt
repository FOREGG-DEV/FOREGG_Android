package com.hugg.data.dto.dailyRecord

import com.hugg.data.base.DataDto
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.EmotionType
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
