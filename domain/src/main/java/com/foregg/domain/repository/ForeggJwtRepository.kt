package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.foregg.domain.model.response.ForeggJwtResponseVo
import kotlinx.coroutines.flow.Flow

interface ForeggJwtRepository {

    suspend fun saveAccessTokenAndRefreshToken(request: SaveForeggJwtRequestVo): Flow<Boolean>
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>
    suspend fun reIssueToken(request : String): Flow<ApiState<ForeggJwtResponseVo>>
}