package com.foregg.data.dto.challenge

import com.foregg.data.base.DataDto

data class MyChallengeResponse(
    val challengeList: List<MyChallengeResponseListItem>,
    val month: Int,
    val week: Int
): DataDto