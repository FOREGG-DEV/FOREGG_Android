package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.SignUpWithTokenRequestVo
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostJoinUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<SignUpWithTokenRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: SignUpWithTokenRequestVo): Flow<ApiState<Unit>> {
        return authRepository.join(request)
    }
}