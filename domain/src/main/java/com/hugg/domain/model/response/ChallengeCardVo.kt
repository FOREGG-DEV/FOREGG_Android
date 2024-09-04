package com.hugg.domain.model.response

import com.hugg.domain.base.DomainResponse


data class ChallengeCardVo(
    val id: Long = -1,
    val name: String = "",
    val description: String = "",
    val participants: Int = 0,
    val image: String = "",
    val ifMine: Boolean = false
): DomainResponse
