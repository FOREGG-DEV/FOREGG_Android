package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.dailyRecord.DailyRecordResponse
import com.foregg.data.dto.dailyRecord.SideEffectResponseListItem
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.foregg.domain.model.request.dailyRecord.EmotionVo
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DailyRecordApi {
    @GET(Endpoints.DailyRecord.DAILY)
    suspend fun getDailyRecord(): Response<ApiResponse<DailyRecordResponse>>
    @GET(Endpoints.DailyRecord.SIDEEFFECTLIST)
    suspend fun getSideEffect(): Response<ApiResponse<List<SideEffectResponseListItem>>>
    @POST(Endpoints.DailyRecord.WRITE)
    suspend fun createDailyRecord(
        @Body dailyRecord: CreateDailyRecordRequestVo
    ): Response<ApiResponse<Unit>>
    @POST(Endpoints.DailyRecord.SIDEEFFECT)
    suspend fun createSideEffect(@Body content: CreateSideEffectRequestVo): Response<ApiResponse<Unit>>
    @PUT(Endpoints.DailyRecord.EMOTION)
    suspend fun putEmotion(
        @Path("id") id: Long,
        @Body request: EmotionVo
    ): Response<ApiResponse<Unit>>
}