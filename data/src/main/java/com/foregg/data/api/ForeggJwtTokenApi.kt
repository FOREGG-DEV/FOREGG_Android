package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.ForeggJwtResponse
import com.foregg.domain.model.request.ForeggJwtReIssueRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForeggJwtTokenApi {
    @POST(Endpoints.AUTH.RENEWAL)
    suspend fun reIssueToken(
        @Body request : ForeggJwtReIssueRequestVo
    ) : Response<ApiResponse<ForeggJwtResponse>>
}