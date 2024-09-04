package com.hugg.domain.usecase.auth

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.fcm.RenewalFcmRequestVo
import com.hugg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRenewalFcmUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<RenewalFcmRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: RenewalFcmRequestVo): Flow<ApiState<Unit>> {
        return authRepository.renewalFcm(request)
    }
}