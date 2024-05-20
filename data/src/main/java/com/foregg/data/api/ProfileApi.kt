package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.profile.ProfileDetailResponse
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {

    @GET(Endpoints.PROFILE.MY_INFO)
    suspend fun getMyInfo() : Response<ApiResponse<ProfileDetailResponse>>

    @PUT(Endpoints.PROFILE.MODIFY)
    suspend fun editMyInfo(
        @Body request : EditMyInfoRequestVo
    ) : Response<ApiResponse<Unit>>
}