package com.hugg.data.api

import com.hugg.data.base.ApiResponse
import com.hugg.data.dto.information.InformationResponse
import com.hugg.domain.model.enums.InformationType
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