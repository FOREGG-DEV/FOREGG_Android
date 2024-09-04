package com.hugg.data.repository

import com.hugg.data.api.DailyRecordApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.dailyRecord.DailyRecordResponseMapper
import com.hugg.data.mapper.dailyRecord.InjectionInfoResponseMapper
import com.hugg.data.mapper.dailyRecord.SideEffectResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.hugg.domain.model.request.dailyRecord.EditDailyRecordRequestVo
import com.hugg.domain.model.request.dailyRecord.PutEmotionVo
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.request.dailyRecord.SideEffectEditRequestVo
import com.hugg.domain.model.response.DailyRecordResponseVo
import com.hugg.domain.model.response.SideEffectListItemVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import com.hugg.domain.repository.DailyRecordRepository
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

    override suspend fun createSideEffect(request: CreateSideEffectRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.createSideEffect(request) }, UnitResponseMapper)
    }

    override suspend fun getSideEffect(): Flow<ApiState<List<SideEffectListItemVo>>> {
        return apiLaunch(apiCall = { dailyRecordApi.getSideEffect() }, SideEffectResponseMapper)
    }

    override suspend fun editSideEffect(request: SideEffectEditRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.editSideEffect(request.id, request.body) }, UnitResponseMapper)
    }

    override suspend fun deleteSideEffect(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.deleteSideEffect(request) }, UnitResponseMapper)
    }

    override suspend fun putEmotion(request: PutEmotionVo): Flow<ApiState<Unit>> {
        return apiLaunch(
            apiCall = { dailyRecordApi.putEmotion(request.id, request.request) },
            UnitResponseMapper
        )
    }
    override suspend fun postShareInjection(request : InjectionAlarmRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.shareInjection(request.id, request.time) }, UnitResponseMapper)
    }

    override suspend fun getInjectionInfo(request: InjectionAlarmRequestVo): Flow<ApiState<InjectionInfoResponseVo>> {
        return apiLaunch(apiCall = { dailyRecordApi.getInjectionInfo(request.id, request.time) }, InjectionInfoResponseMapper)
    }

    override suspend fun deleteDailyRecord(id: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.deleteDailyRecord(id) }, UnitResponseMapper)
    }

    override suspend fun editDailyRecord(request: EditDailyRecordRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { dailyRecordApi.editDailyRecord(id = request.id, request = request.request) }, UnitResponseMapper)
    }
}