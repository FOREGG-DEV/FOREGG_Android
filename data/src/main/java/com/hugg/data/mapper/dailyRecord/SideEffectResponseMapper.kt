package com.hugg.data.mapper.dailyRecord

import com.hugg.data.base.Mapper
import com.hugg.data.dto.dailyRecord.SideEffectResponseListItem
import com.hugg.domain.model.response.SideEffectListItemVo

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