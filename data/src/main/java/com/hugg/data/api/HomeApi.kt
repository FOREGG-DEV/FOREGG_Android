package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Endpoints.Home.HOME)
    suspend fun getHome() : Response<ApiResponse<HomeResponse>>
}