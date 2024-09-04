package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.SignResponse
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    companion object{
        const val HEADER_TOKEN = "accessToken"
    }
    @POST(Endpoints.AUTH.LOGIN)
    suspend fun login(
        @Header(HEADER_TOKEN) request : String
    ) : Response<ApiResponse<SignResponse>>

    @POST(Endpoints.AUTH.JOIN)
    suspend fun join(
        @Header(HEADER_TOKEN) accessToken : String,
        @Body request : SignUpRequestVo
    ) : Response<ApiResponse<SignResponse>>

    @POST(Endpoints.AUTH.JOIN_MALE)
    suspend fun joinMale(
        @Header(HEADER_TOKEN) accessToken : String,
        @Body request : SignUpMaleRequestVo
    ) : Response<ApiResponse<SignResponse>>

    @GET(Endpoints.AUTH.GET_SHARE_CODE)
    suspend fun getShareCode() : Response<ApiResponse<SignResponse>>
}