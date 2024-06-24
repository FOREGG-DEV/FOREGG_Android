package com.foregg.domain.usecase.home.challenge

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.challenge.MarkChallengeVisitRequestVo
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkChallengeVisitUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<MarkChallengeVisitRequestVo, Boolean>() {
    override suspend fun invoke(request: MarkChallengeVisitRequestVo): Flow<Boolean> {
        return challengeRepository.markVisit(request.id, request.time)
    }
}