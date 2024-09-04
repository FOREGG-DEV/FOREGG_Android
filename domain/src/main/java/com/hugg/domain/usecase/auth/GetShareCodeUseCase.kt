package com.hugg.domain.usecase.auth

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.ShareCodeResponseVo
import com.hugg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShareCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<Unit, ApiState<ShareCodeResponseVo>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<ShareCodeResponseVo>> {
        return authRepository.getShareCode()
    }
}