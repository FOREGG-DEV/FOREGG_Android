package com.foregg.domain.model.response

import com.google.gson.annotations.SerializedName

data class SideEffectListItemVo (
    val content: String,
    val date: String,
    val id: Long
)