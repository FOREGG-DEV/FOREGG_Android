package com.hugg.data.mapper.dailyRecord

import com.hugg.data.base.Mapper
import com.hugg.data.dto.dailyRecord.DailyRecordResponse
import com.hugg.domain.model.enums.EmotionType
import com.hugg.domain.model.response.DailyRecordResponseVo
import com.hugg.domain.model.vo.DailyRecordResponseItemVo

object DailyRecordResponseMapper: Mapper.ResponseMapper<DailyRecordResponse, DailyRecordResponseVo> {
    override fun mapDtoToModel(type: DailyRecordResponse?): DailyRecordResponseVo {
        return type?.run {
            DailyRecordResponseVo(
                dailyResponseDto = type.dailyResponseDTO.map {
                    DailyRecordResponseItemVo(
                        id = it.id,
                        date = it.date,
                        content = it.content,
                        dailyConditionType = it.dailyConditionType,
                        emotionType = it.emotionType ?: EmotionType.DEFAULT
                    )
                }
            )
        } ?: DailyRecordResponseVo(emptyList())
    }
}