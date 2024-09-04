package com.hugg.domain.usecase.home.challenge

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.MyChallengeListItemVo
import com.hugg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
): UseCase<Unit, ApiState<List<MyChallengeListItemVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<MyChallengeListItemVo>>> {
        return challengeRepository.getMyChallenge()
    }
}