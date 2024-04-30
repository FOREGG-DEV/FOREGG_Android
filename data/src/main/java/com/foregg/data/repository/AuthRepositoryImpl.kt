package com.foregg.data.repository

import com.foregg.data.api.AuthApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.SignResponseMapper
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository, BaseRepository() {
    override suspend fun login(request: String): Flow<ApiState<SignResponseVo>> {
        return apiLaunch(apiCall = { authApi.login(request) }, SignResponseMapper )
    }

    override suspend fun join(request: SignUpWithTokenRequestVo): Flow<ApiState<SignResponseVo>> {
        return apiLaunch(apiCall = { authApi.join(request.accessToken, request.signUpRequestVo)}, SignResponseMapper )
    }
}