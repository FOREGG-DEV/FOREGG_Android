package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.foregg.domain.model.response.SignResponseVo
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostJoinMaleUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<SignUpWithTokenMaleRequestVo, ApiState<SignResponseVo>>() {
    override suspend operator fun invoke(request: SignUpWithTokenMaleRequestVo): Flow<ApiState<SignResponseVo>> {
        return authRepository.joinMale(request)
    }
}