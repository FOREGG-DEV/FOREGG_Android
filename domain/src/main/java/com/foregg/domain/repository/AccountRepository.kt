package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.AccountGetConditionRequestVo
import com.foregg.domain.model.response.AccountResponseVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getByCondition(request : AccountGetConditionRequestVo) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByCount(request : Int) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByMonth(request : String) : Flow<ApiState<AccountResponseVo>>
    suspend fun delete(request : Long) : Flow<ApiState<Unit>>
}