package com.foregg.data.mapper.challenge

import com.foregg.data.base.Mapper
import com.foregg.data.dto.challenge.ChallengeResponseListItem
import com.foregg.domain.model.response.ChallengeCardVo

object ChallengeResponseMapper: Mapper.ResponseMapper<List<ChallengeResponseListItem>, List<ChallengeCardVo>> {
    override fun mapDtoToModel(type: List<ChallengeResponseListItem>?): List<ChallengeCardVo> {
        return type?.map { listItem ->
            ChallengeCardVo(
                id = listItem.id,
                name = listItem.name,
                description = listItem.description,
                participants = listItem.participants,
                image = listItem.image ?: "",
                ifMine = listItem.ifMine
            )
        } ?: emptyList()
    }
}