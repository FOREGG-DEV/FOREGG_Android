package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.domain.model.request.fcm.RenewalFcmRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST(Endpoints.AUTH.RENEWAL_FCM)
    suspend fun renewalFcm(
        @Body request : RenewalFcmRequestVo
    ) : Response<ApiResponse<Unit>>
}