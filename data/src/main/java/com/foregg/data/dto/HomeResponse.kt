package com.foregg.data.dto

import com.foregg.data.base.DataDto
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("todayDate")
    val todayDate: String,
    @SerializedName("homeRecordResponseDTO")
    val homeRecordResponseVo: List<HomeRecordResponseVo>
) : DataDto
