package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.domain.model.request.SignUpRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST(Endpoints.AUTH.LOGIN)
    suspend fun login(
        @Header("accessToken") request : String
    ) : Response<ApiResponse<Unit>>

    @POST(Endpoints.AUTH.JOIN)
    suspend fun join(
        @Header("accessToken") accessToken : String,
        @Body request : SignUpRequestVo
    ) : Response<ApiResponse<Unit>>
}