package com.foregg.domain.model.response

import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.vo.DailyRecordResponseItemVo

data class DailyRecordResponseVo (
    val dailyResponseDto: List<DailyRecordResponseItemVo>
): DomainResponse