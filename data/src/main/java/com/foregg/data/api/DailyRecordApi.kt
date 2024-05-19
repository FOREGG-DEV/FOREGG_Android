package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.dailyRecord.DailyRecordResponse
import retrofit2.Response
import retrofit2.http.GET

interface DailyRecordApi {
    @GET(Endpoints.DailyRecord.DAILY)
    suspend fun getDailyRecord(): Response<ApiResponse<DailyRecordResponse>>
}