package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.HomeResponse
import com.foregg.data.dto.information.InformationResponse
import com.foregg.domain.model.enums.InformationType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InformationApi {

    companion object {
        const val QUERY_SORT = "sort"
    }

    @GET(Endpoints.INFORMATION.ALL)
    suspend fun getAllInformation() : Response<ApiResponse<List<InformationResponse>>>

    @GET(Endpoints.INFORMATION.BY_TYPE)
    suspend fun getInformationByType(
        @Query(QUERY_SORT) sort : InformationType
    ) : Response<ApiResponse<List<InformationResponse>>>
}