package com.hugg.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleSideEffectResponse(
    @SerializedName("medicalRecord")
    val medicalRecord : String? = "",
    @SerializedName("sideEffects")
    val sideEffects : List<SideEffect>? = emptyList()
)

data class SideEffect(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("date")
    val date : String = "",
    @SerializedName("content")
    val content : String = ""
)