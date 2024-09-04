package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.ForeggJwtResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface ForeggJwtTokenApi {
    @POST(Endpoints.AUTH.RENEWAL)
    suspend fun reIssueToken(
        @Header("Authorization") refreshToken : String,
    ) : Response<ApiResponse<ForeggJwtResponse>>
}