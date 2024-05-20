package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.dailyRecord.DailyRecordResponse
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface DailyRecordApi {
    @GET(Endpoints.DailyRecord.DAILY)
    suspend fun getDailyRecord(): Response<ApiResponse<DailyRecordResponse>>
    @POST(Endpoints.DailyRecord.WRITE)
    suspend fun createDailyRecord(dailyRecord: CreateDailyRecordRequestVo): Response<ApiResponse<Unit>>
}