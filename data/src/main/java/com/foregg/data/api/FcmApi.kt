package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.domain.model.request.fcm.RenewalFcmRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST(Endpoints.AUTH.RENEWAL_FCM)
    suspend fun renewalFcm(
        @Body request : RenewalFcmRequestVo
    ) : Response<ApiResponse<Unit>>
}