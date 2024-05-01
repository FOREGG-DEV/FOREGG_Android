package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.SignResponseVo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request : String) : Flow<ApiState<SignResponseVo>>
    suspend fun join(request: SignUpWithTokenRequestVo) : Flow<ApiState<SignResponseVo>>
}