package com.foregg.domain.usecase.home.challenge

import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteChallengeVisitUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : UseCase<Long, Boolean>() {
    override suspend fun invoke(request: Long): Flow<Boolean> {
        return challengeRepository.removeVisitId(request)
    }
}