package com.foregg.domain.usecase.home.challenge

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
): UseCase<Unit, ApiState<List<MyChallengeListItemVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<MyChallengeListItemVo>>> {
        return challengeRepository.getMyChallenge()
    }
}