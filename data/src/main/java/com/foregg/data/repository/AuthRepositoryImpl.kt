package com.foregg.data.repository

import com.foregg.data.api.AuthApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.ShareCodeResponseMapper
import com.foregg.data.mapper.SignResponseMapper
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.fcm.RenewalFcmRequestVo
import com.foregg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.foregg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.ShareCodeResponseVo
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

    override suspend fun joinMale(request: SignUpWithTokenMaleRequestVo): Flow<ApiState<SignResponseVo>> {
        return apiLaunch(apiCall = { authApi.joinMale(request.accessToken, request.signUpMaleRequestVo)}, SignResponseMapper )
    }

    override suspend fun getShareCode(): Flow<ApiState<ShareCodeResponseVo>> {
        return apiLaunch(apiCall = { authApi.getShareCode()}, ShareCodeResponseMapper )
    }

    override suspend fun renewalFcm(request: RenewalFcmRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { authApi.renewalFcm(request)}, UnitResponseMapper )
    }
}