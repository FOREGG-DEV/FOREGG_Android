package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo
import com.google.gson.annotations.SerializedName

data class CreateSideEffectRequestVo(
    @SerializedName("content")
    val content: String = ""
): RequestVo
