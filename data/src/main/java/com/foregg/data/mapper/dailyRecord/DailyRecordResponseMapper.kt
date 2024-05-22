package com.foregg.data.mapper.dailyRecord

import com.foregg.data.base.Mapper
import com.foregg.data.dto.dailyRecord.DailyRecordResponse
import com.foregg.domain.model.enums.EmotionType
import com.foregg.domain.model.response.DailyRecordResponseVo
import com.foregg.domain.model.vo.DailyRecordResponseItemVo

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