package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.DailyRecordResponseVo
import kotlinx.coroutines.flow.Flow

interface DailyRecordRepository {
    suspend fun getDailyRecord(): Flow<ApiState<DailyRecordResponseVo>>
}