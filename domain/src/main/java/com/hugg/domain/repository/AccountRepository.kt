package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.account.AccountCreateRequestVo
import com.hugg.domain.model.request.account.AccountEditRequestVo
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.response.account.AccountDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getByCondition(request : AccountGetConditionRequestVo) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByCount(request : Int) : Flow<ApiState<AccountResponseVo>>
    suspend fun getByMonth(request : String) : Flow<ApiState<AccountResponseVo>>
    suspend fun delete(request : Long) : Flow<ApiState<Unit>>
    suspend fun getAccountDetail(request : Long) : Flow<ApiState<AccountDetailResponseVo>>
    suspend fun createAccount(request : AccountCreateRequestVo) : Flow<ApiState<Unit>>
    suspend fun editAccount(request : AccountEditRequestVo) : Flow<ApiState<Unit>>
}