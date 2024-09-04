package com.hugg.domain.usecase.home.challenge

import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.challenge.MarkChallengeVisitRequestVo
import com.hugg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkChallengeVisitUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<MarkChallengeVisitRequestVo, Boolean>() {
    override suspend fun invoke(request: MarkChallengeVisitRequestVo): Flow<Boolean> {
        return challengeRepository.markVisit(request.id, request.time)
    }
}