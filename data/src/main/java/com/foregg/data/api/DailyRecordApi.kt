package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.dailyRecord.DailyRecordResponse
import com.foregg.data.dto.dailyRecord.InjectionInfoResponse
import com.foregg.data.dto.dailyRecord.SideEffectResponseListItem
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.foregg.domain.model.request.dailyRecord.EmotionVo
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DailyRecordApi {

    companion object{
        const val PATH_ID = "id"
        const val QUERY_TIME = "time"
    }
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

    @PUT(Endpoints.DailyRecord.SIDE_EFFECT_EDIT)
    suspend fun editSideEffect(
        @Path(PATH_ID) id : Long,
        @Body content: CreateSideEffectRequestVo
    ): Response<ApiResponse<Unit>>

    @DELETE(Endpoints.DailyRecord.SIDE_EFFECT_EDIT)
    suspend fun deleteSideEffect(
        @Path(PATH_ID) id : Long,
    ): Response<ApiResponse<Unit>>

    @PUT(Endpoints.DailyRecord.EMOTION)
    suspend fun putEmotion(
        @Path(PATH_ID) id: Long,
        @Body request: EmotionVo
    ): Response<ApiResponse<Unit>>
    @POST(Endpoints.DailyRecord.SHARE_INJECTION)
    suspend fun shareInjection(
        @Path(PATH_ID) id : Long,
        @Query(QUERY_TIME) time : String,
    ): Response<ApiResponse<Unit>>
    @GET(Endpoints.DailyRecord.GET_INJECTION_INFO)
    suspend fun getInjectionInfo(
        @Path(PATH_ID) id : Long,
        @Query(QUERY_TIME) time : String,
    ): Response<ApiResponse<InjectionInfoResponse>>
    @DELETE(Endpoints.DailyRecord.ID)
    suspend fun deleteDailyRecord(
        @Path(PATH_ID) id: Long
    ): Response<ApiResponse<Unit>>

    @PUT(Endpoints.DailyRecord.ID)
    suspend fun editDailyRecord(
        @Path(PATH_ID) id: Long,
        @Body request: CreateDailyRecordRequestVo
    ): Response<ApiResponse<Unit>>
}