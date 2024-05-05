package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.account.AccountCreateEditRequestVo
import com.foregg.domain.model.request.account.AccountGetConditionRequestVo
import com.foregg.domain.model.response.account.AccountResponseVo
import com.foregg.domain.model.response.account.AccountDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getByCondition(request : AccountGetConditionRequestVo) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByCount(request : Int) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByMonth(request : String) : Flow<ApiState<AccountResponseVo>>
    suspend fun delete(request : Long) : Flow<ApiState<Unit>>
    suspend fun getAccountDetail(request : Long) : Flow<ApiState<AccountDetailResponseVo>>
    suspend fun createAccount(request : AccountCreateEditRequestVo) : Flow<ApiState<Unit>>
    suspend fun editAccount(request : AccountCreateEditRequestVo) : Flow<ApiState<Unit>>
}