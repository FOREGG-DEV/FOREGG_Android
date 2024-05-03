package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.AddMedicalRecordRequest
import com.foregg.domain.model.request.ScheduleDetailRequestVo
import com.foregg.domain.model.vo.MedicalRecord
import com.foregg.domain.model.vo.ScheduleDetailVo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getScheduleList(request : String) : Flow<ApiState<List<ScheduleDetailVo>>>
    suspend fun deleteSchedule(request: Long) : Flow<ApiState<Unit>>
    suspend fun getDetailRecord(request : Long) : Flow<ApiState<ScheduleDetailVo>>
    suspend fun addSchedule(request : ScheduleDetailRequestVo) : Flow<ApiState<Unit>>
    suspend fun modifySchedule(id : Long, request : ScheduleDetailRequestVo) : Flow<ApiState<Unit>>
    suspend fun addMedicalRecord(id : Long, request : AddMedicalRecordRequest) : Flow<ApiState<Unit>>
    suspend fun getMedicalRecordAndSideEffect(id : Long) : Flow<ApiState<MedicalRecord>>
}