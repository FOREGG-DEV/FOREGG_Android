package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.fcm.RenewalFcmRequestVo
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRenewalFcmUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<RenewalFcmRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: RenewalFcmRequestVo): Flow<ApiState<Unit>> {
        return authRepository.renewalFcm(request)
    }
}