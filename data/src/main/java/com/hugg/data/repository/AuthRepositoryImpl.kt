package com.hugg.data.repository

import com.hugg.data.api.AuthApi
import com.hugg.data.api.FcmApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.ShareCodeResponseMapper
import com.hugg.data.mapper.SignResponseMapper
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.fcm.RenewalFcmRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.hugg.domain.model.response.ShareCodeResponseVo
import com.hugg.domain.model.response.SignResponseVo
import com.hugg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val fcmApi: FcmApi
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
        return apiLaunch(apiCall = { fcmApi.renewalFcm(request)}, UnitResponseMapper )
    }
}