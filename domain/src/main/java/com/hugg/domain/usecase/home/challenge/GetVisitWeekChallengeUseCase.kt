package com.hugg.domain.usecase.home.challenge

import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVisitWeekChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<Long, String>() {
    override suspend fun invoke(request: Long): Flow<String> {
        return challengeRepository.getVisitWeek(request)
    }
}