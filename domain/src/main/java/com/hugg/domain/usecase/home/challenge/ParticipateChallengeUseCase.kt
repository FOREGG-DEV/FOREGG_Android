package com.hugg.domain.usecase.home.challenge

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParticipateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<Long, ApiState<Unit>>() {
    override suspend fun invoke(request: Long): Flow<ApiState<Unit>> {
        return challengeRepository.participateChallenge(request)
    }
}