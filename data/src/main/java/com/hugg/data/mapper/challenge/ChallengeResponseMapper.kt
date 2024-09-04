package com.hugg.data.mapper.challenge

import com.hugg.data.base.Mapper
import com.hugg.data.dto.challenge.ChallengeResponseListItem
import com.hugg.domain.model.response.ChallengeCardVo

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