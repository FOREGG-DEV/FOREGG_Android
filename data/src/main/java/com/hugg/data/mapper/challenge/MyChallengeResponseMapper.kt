package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.MyChallengeResponseListItem
import com.hugg.domain.model.response.MyChallengeListItemVo

object MyChallengeResponseMapper: Mapper.ResponseMapper<List<MyChallengeResponseListItem>, List<MyChallengeListItemVo>> {
    override fun mapDtoToModel(type: List<MyChallengeResponseListItem>?): List<MyChallengeListItemVo> {
        return type?.map {  it ->
            MyChallengeListItemVo(
                description = it.description,
                image = it.image ?: "",
                id = it.id,
                name = it.name,
                participants = it.participants,
                successDays = it.successDays,
                weekOfMonth = it.weekOfMonth,
                lastSaturday = it.lastSaturday
            )
        } ?: emptyList()
    }
}