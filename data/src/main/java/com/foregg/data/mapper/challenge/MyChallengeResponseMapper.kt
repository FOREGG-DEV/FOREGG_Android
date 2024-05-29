package com.foregg.data.mapper.challenge

import com.foregg.data.base.Mapper
import com.foregg.data.dto.challenge.MyChallengeResponseListItem
import com.foregg.domain.model.response.MyChallengeListItemVo

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
                weekOfMonth = it.weekOfMonth
            )
        } ?: emptyList()
    }
}