package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.ForeggJwtResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface ForeggJwtTokenApi {
    @POST(Endpoints.AUTH.RENEWAL)
    suspend fun reIssueToken(
        @Header("Authorization") refreshToken : String,
    ) : Response<ApiResponse<ForeggJwtResponse>>
}