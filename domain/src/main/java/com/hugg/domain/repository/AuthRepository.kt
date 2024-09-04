package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.fcm.RenewalFcmRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.hugg.domain.model.response.ShareCodeResponseVo
import com.hugg.domain.model.response.SignResponseVo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request : String) : Flow<ApiState<SignResponseVo>>
    suspend fun join(request: SignUpWithTokenRequestVo) : Flow<ApiState<SignResponseVo>>
    suspend fun joinMale(request: SignUpWithTokenMaleRequestVo) : Flow<ApiState<SignResponseVo>>
    suspend fun getShareCode() : Flow<ApiState<ShareCodeResponseVo>>
    suspend fun renewalFcm(request: RenewalFcmRequestVo) : Flow<ApiState<Unit>>
}