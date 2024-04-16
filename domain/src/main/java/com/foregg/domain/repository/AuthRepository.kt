package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.DomainResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request : String) : Flow<ApiState<Unit>>
}