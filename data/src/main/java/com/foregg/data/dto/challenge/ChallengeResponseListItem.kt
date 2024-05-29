package com.foregg.data.dto.challenge

import com.foregg.data.base.DataDto
import com.google.gson.annotations.SerializedName

data class ChallengeResponseListItem(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("participants")
    val participants: Int = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("ifMine")
    val ifMine: Boolean = false
): DataDto
