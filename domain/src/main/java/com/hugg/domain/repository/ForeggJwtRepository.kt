package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.response.ForeggJwtResponseVo
import kotlinx.coroutines.flow.Flow

interface ForeggJwtRepository {

    suspend fun saveAccessTokenAndRefreshToken(request: SaveForeggJwtRequestVo): Flow<Boolean>
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>
    suspend fun reIssueToken(request : String): Flow<ApiState<ForeggJwtResponseVo>>
}