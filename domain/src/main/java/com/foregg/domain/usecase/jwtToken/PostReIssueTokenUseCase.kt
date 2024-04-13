package com.foregg.domain.usecase.jwtToken

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.ForeggJwtReIssueRequestVo
import com.foregg.domain.model.response.ForeggJwtResponseVo
import com.foregg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostReIssueTokenUseCase @Inject constructor(
    private val plubJwtRepository: ForeggJwtRepository
): UseCase<ForeggJwtReIssueRequestVo, ApiState<ForeggJwtResponseVo>>() {
    override suspend operator fun invoke(request: ForeggJwtReIssueRequestVo): Flow<ApiState<ForeggJwtResponseVo>> {
        return plubJwtRepository.reIssueToken(request)
    }
}