package com.foregg.data.dto.challenge

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class MyChallengeResponseListItem(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("participants")
    val participants: Int,
    @SerializedName("successDays")
    val successDays: List<String>,
    @SerializedName("weekOfMonth")
    val weekOfMonth: String = ""
): DataDto