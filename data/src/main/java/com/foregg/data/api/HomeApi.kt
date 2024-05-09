package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET(Endpoints.Home.HOME)
    suspend fun getHome() : Response<ApiResponse<HomeResponse>>
}