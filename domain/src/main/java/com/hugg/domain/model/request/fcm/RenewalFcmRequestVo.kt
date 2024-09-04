package com.hugg.domain.model.request.fcm

import com.google.gson.annotations.SerializedName

data class RenewalFcmRequestVo(
    @SerializedName("fcm")
    val fcm : String
)
