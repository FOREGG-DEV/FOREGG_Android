package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.foregg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.foregg.domain.model.response.ShareCodeResponseVo
import com.foregg.domain.model.response.SignResponseVo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request : String) : Flow<ApiState<SignResponseVo>>
    suspend fun join(request: SignUpWithTokenRequestVo) : Flow<ApiState<SignResponseVo>>
    suspend fun joinMale(request: SignUpWithTokenMaleRequestVo) : Flow<ApiState<SignResponseVo>>
    suspend fun getShareCode() : Flow<ApiState<ShareCodeResponseVo>>
}