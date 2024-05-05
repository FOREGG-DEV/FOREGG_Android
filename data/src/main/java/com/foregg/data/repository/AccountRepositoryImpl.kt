package com.foregg.data.repository

import com.foregg.data.api.AccountApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.data.mapper.account.AccountDetailResponseMapper
import com.foregg.data.mapper.account.AccountItemResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.account.AccountCreateEditRequestVo
import com.foregg.domain.model.request.account.AccountGetConditionRequestVo
import com.foregg.domain.model.response.account.AccountResponseVo
import com.foregg.domain.model.response.account.AccountDetailResponseVo
import com.foregg.domain.repository.AccountRepository
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

    override suspend fun createAccount(request: AccountCreateEditRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.createAccount(request) }, UnitResponseMapper )
    }

    override suspend fun editAccount(request: AccountCreateEditRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { accountApi.modifyAccount(request) }, UnitResponseMapper )
    }
}