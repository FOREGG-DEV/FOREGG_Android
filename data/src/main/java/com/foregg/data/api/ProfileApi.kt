package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.profile.ProfileDetailResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET(Endpoints.PROFILE.MY_INFO)
    suspend fun getMyInfo() : Response<ApiResponse<ProfileDetailResponse>>
}