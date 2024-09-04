package com.hugg.domain.usecase.jwtToken

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.ForeggJwtResponseVo
import com.hugg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtRepository: ForeggJwtRepository
): UseCase<String, ApiState<ForeggJwtResponseVo>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<ForeggJwtResponseVo>> {
        val newRequest = "Bearer $request"
        return plubJwtRepository.reIssueToken(newRequest)
    }
}