package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.DomainResponse
import com.foregg.domain.model.request.SignUpWithTokenRequestVo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request : String) : Flow<ApiState<Unit>>
    suspend fun join(request: SignUpWithTokenRequestVo) : Flow<ApiState<Unit>>
}