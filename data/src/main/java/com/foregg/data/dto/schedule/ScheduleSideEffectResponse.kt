package com.foregg.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleSideEffectResponse(
    @SerializedName("medicalRecord")
    val medicalRecord : String = "",
    @SerializedName("sideEffects")
    val sideEffects : List<SideEffect>? = emptyList()
)

data class SideEffect(
    @SerializedName("content")
    val content : String = ""
)