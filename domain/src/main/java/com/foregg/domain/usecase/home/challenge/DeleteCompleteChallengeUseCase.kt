package com.foregg.domain.usecase.home.challenge

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCompleteChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<Long, ApiState<Unit>>() {
    override suspend fun invoke(request: Long): Flow<ApiState<Unit>> {
        return challengeRepository.deleteCompleteChallenge(request)
    }
}