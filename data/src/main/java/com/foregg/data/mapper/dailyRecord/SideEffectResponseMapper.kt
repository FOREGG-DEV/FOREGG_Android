package com.foregg.data.mapper.dailyRecord

import com.foregg.data.base.Mapper
import com.foregg.data.dto.dailyRecord.SideEffectResponseListItem
import com.foregg.domain.model.response.SideEffectListItemVo

object SideEffectResponseMapper : Mapper.ResponseMapper<List<SideEffectResponseListItem>, List<SideEffectListItemVo>>{
    override fun mapDtoToModel(type: List<SideEffectResponseListItem>?): List<SideEffectListItemVo> {
        return type?.map {
            SideEffectListItemVo(
                content = it.content,
                id = it.id,
                date = it.date
            )
        } ?: emptyList()
    }
}