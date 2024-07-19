package com.foregg.domain.model.request.dailyRecord

import com.foregg.domain.base.RequestVo
import com.google.gson.annotations.SerializedName

data class SideEffectEditRequestVo(
    val id : Long = -1,
    val body : CreateSideEffectRequestVo = CreateSideEffectRequestVo()
): RequestVo

data class CreateSideEffectRequestVo(
    @SerializedName("content")
    val content: String = ""
): RequestVo
