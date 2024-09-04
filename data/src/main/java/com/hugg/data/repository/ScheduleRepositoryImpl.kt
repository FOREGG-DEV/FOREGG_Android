package com.hugg.data.repository

import com.hugg.data.api.ScheduleApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.schedule.ScheduleDetailResponseMapper
import com.hugg.data.mapper.schedule.ScheduleListResponseMapper
import com.hugg.data.mapper.schedule.ScheduleSideEffectResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.AddMedicalRecordRequest
import com.hugg.domain.model.request.ScheduleDetailRequestVo
import com.hugg.domain.model.vo.MedicalRecord
import com.hugg.domain.model.vo.ScheduleDetailVo
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi
) : ScheduleRepository, BaseRepository() {

    override suspend fun getScheduleList(request: String): Flow<ApiState<List<ScheduleDetailVo>>> {
        return apiLaunch(apiCall = { scheduleApi.getScheduleList(request) }, ScheduleListResponseMapper )
    }

    override suspend fun deleteSchedule(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { scheduleApi.deleteSchedule(request) }, UnitResponseMapper )
    }

    override suspend fun getDetailRecord(request: Long): Flow<ApiState<ScheduleDetailVo>> {
        return apiLaunch(apiCall = { scheduleApi.getDetailSchedule(request) }, ScheduleDetailResponseMapper)
    }

    override suspend fun addSchedule(request: ScheduleDetailRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { scheduleApi.addSchedule(request) }, UnitResponseMapper)
    }

    override suspend fun modifySchedule(id: Long, request: ScheduleDetailRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { scheduleApi.modify(id, request) }, UnitResponseMapper)
    }

    override suspend fun addMedicalRecord(id: Long, request: AddMedicalRecordRequest): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { scheduleApi.addMedicalRecord(id, request) }, UnitResponseMapper)
    }

    override suspend fun getMedicalRecordAndSideEffect(id: Long): Flow<ApiState<MedicalRecord>> {
        return apiLaunch(apiCall = { scheduleApi.getMedicalRecordAndSideEffect(id) }, ScheduleSideEffectResponseMapper)
    }
}