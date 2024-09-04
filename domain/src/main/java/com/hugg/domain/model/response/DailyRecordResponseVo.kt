package com.hugg.domain.model.response

import com.hugg.domain.base.DomainResponse
import com.hugg.domain.model.vo.DailyRecordResponseItemVo

data class DailyRecordResponseVo (
    val dailyResponseDto: List<DailyRecordResponseItemVo>
): DomainResponse