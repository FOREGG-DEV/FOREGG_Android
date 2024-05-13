package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.ShareCodeResponseVo
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShareCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<Unit, ApiState<ShareCodeResponseVo>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<ShareCodeResponseVo>> {
        return authRepository.getShareCode()
    }
}