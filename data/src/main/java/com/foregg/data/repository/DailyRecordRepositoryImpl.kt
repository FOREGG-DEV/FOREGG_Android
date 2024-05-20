package com.foregg.data.repository

import com.foregg.data.api.DailyRecordApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.data.mapper.dailyRecord.DailyRecordResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.model.response.DailyRecordResponseVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyRecordRepositoryImpl @Inject constructor(
    private val dailyRecordApi: DailyRecordApi
): DailyRecordRepository, BaseRepository() {
    override suspend fun getDailyRecord(): Flow<ApiState<DailyRecordResponseVo>> {
        return apiLaunch(apiCall =  { dailyRecordApi.getDailyRecord() }, DailyRecordResponseMapper)
    }

    override suspend fun createDailyRecord(request: CreateDailyRecordRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.createDailyRecord(request) }, UnitResponseMapper)
    }
}