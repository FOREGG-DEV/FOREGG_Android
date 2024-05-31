package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.foregg.domain.model.response.DailyRecordResponseVo
import com.foregg.domain.model.response.SideEffectListItemVo
import kotlinx.coroutines.flow.Flow

interface DailyRecordRepository {
    suspend fun getDailyRecord(): Flow<ApiState<DailyRecordResponseVo>>
    suspend fun createDailyRecord(request: CreateDailyRecordRequestVo): Flow<ApiState<Unit>>
    suspend fun createSideEffect(request: CreateSideEffectRequestVo): Flow<ApiState<Unit>>
    suspend fun getSideEffect(): Flow<ApiState<List<SideEffectListItemVo>>>
    suspend fun postShareInjection() : Flow<ApiState<Unit>>
}