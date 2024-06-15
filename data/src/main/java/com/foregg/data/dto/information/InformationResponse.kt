package com.foregg.data.dto.information

import com.foregg.data.base.DataDto
import com.foregg.domain.model.enums.InformationType
import com.google.gson.annotations.SerializedName

data class InformationResponse(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("informationType")
    val informationType : InformationType = InformationType.NOTHING,
    @SerializedName("tag")
    val tag : List<String> = emptyList(),
    @SerializedName("image")
    val image : String = "",
    @SerializedName("url")
    val url : String = "",
) : DataDto
