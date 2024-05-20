package com.foregg.domain.usecase.home.challenge

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<Unit, ApiState<List<ChallengeCardVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<ChallengeCardVo>>> {
        return challengeRepository.getAllChallenge()
    }
}