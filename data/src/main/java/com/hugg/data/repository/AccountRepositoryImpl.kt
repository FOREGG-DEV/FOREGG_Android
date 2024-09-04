package com.hugg.data.repository

import com.hugg.data.api.AccountApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.account.AccountDetailResponseMapper
import com.hugg.data.mapper.account.AccountItemResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.account.AccountCreateRequestVo
import com.hugg.domain.model.request.account.AccountEditRequestVo
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.response.account.AccountDetailResponseVo
import com.hugg.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountRepository, BaseRepository() {

    override suspend fun getByCondition(request: AccountGetConditionRequestVo): Flow<ApiState<AccountResponseVo>> {
        return apiLaunch(apiCall = { accountApi.getByCondition(request.from, request.to) }, AccountItemResponseMapper )
    }

    override suspend fun getByCount(request: Int): Flow<ApiState<AccountResponseVo>> {
        return apiLaunch(apiCall = { accountApi.getByCount(request) }, AccountItemResponseMapper )
    }

    override suspend fun getByMonth(request: String): Flow<ApiState<AccountResponseVo>> {
        return apiLaunch(apiCall = { accountApi.getByMonth(request) }, AccountItemResponseMapper )
    }

    override suspend fun delete(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.delete(request) }, UnitResponseMapper )
    }

    override suspend fun getAccountDetail(request: Long): Flow<ApiState<AccountDetailResponseVo>> {
        return apiLaunch(apiCall = { accountApi.getAccountDetail(request) }, AccountDetailResponseMapper )
    }

    override suspend fun createAccount(request: AccountCreateRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.createAccount(request) }, UnitResponseMapper )
    }

    override suspend fun editAccount(request: AccountEditRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.modifyAccount(request.id, request.request) }, UnitResponseMapper )
    }
}