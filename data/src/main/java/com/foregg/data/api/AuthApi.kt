package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST(Endpoints.AUTH.LOGIN)
    suspend fun login(
        @Header("accessToken") request : String
    ) : Response<ApiResponse<Unit>>
}