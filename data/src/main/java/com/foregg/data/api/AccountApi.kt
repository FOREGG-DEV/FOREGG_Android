package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.account.AccountResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountApi {

    companion object{
        const val QUERY_FROM = "from"
        const val QUERY_TO = "to"
        const val QUERY_COUNT = "count"
        const val QUERY_YEARMONTH = "yearmonth"
        const val PATH_ID = "id"
    }
    @GET(Endpoints.ACCOUNT.GET_BY_CONDITION)
    suspend fun getByCondition(
        @Query(QUERY_FROM) from : String,
        @Query(QUERY_TO) to : String
    ) : Response<ApiResponse<AccountResponse>>

    @GET(Endpoints.ACCOUNT.GET_BY_COUNT)
    suspend fun getByCount(
        @Query(QUERY_COUNT) count : Int,
    ) : Response<ApiResponse<AccountResponse>>

    @GET(Endpoints.ACCOUNT.GET_BY_MONTH)
    suspend fun getByMonth(
        @Query(QUERY_YEARMONTH) yearmonth : String,
    ) : Response<ApiResponse<AccountResponse>>

    @DELETE(Endpoints.ACCOUNT.DELETE)
    suspend fun delete(
        @Path(PATH_ID) id : Long,
    ) : Response<ApiResponse<Unit>>
}