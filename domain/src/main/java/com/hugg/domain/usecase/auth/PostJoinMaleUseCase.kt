package com.hugg.domain.usecase.auth

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.hugg.domain.model.response.SignResponseVo
import com.hugg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostJoinMaleUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<SignUpWithTokenMaleRequestVo, ApiState<SignResponseVo>>() {
    override suspend operator fun invoke(request: SignUpWithTokenMaleRequestVo): Flow<ApiState<SignResponseVo>> {
        return authRepository.joinMale(request)
    }
}