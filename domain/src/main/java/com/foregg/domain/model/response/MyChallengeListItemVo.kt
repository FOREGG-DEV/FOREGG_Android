package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse
import com.google.gson.annotations.SerializedName

data class MyChallengeListItemVo (
    val description: String = "",
    val id: Long = 0,
    val image: String = "",
    val name: String = "",
    val participants: Int = 0,
    val successDays: List<String>? = emptyList(),
    val weekOfMonth: String = "",
    val lastSaturday : Boolean = false
): DomainResponse