package com.foregg.data.repository

import com.foregg.data.api.ChallengeApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.data.mapper.challenge.ChallengeResponseMapper
import com.foregg.data.mapper.challenge.MyChallengeResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeApi: ChallengeApi
) : ChallengeRepository, BaseRepository() {
    override suspend fun getAllChallenge(): Flow<ApiState<List<ChallengeCardVo>>> {
        return apiLaunch(apiCall = { challengeApi.getAllChallenge() }, ChallengeResponseMapper)
    }

    override suspend fun participateChallenge(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.participateChallenge(request)}, UnitResponseMapper)
    }

    override suspend fun getMyChallenge(): Flow<ApiState<List<MyChallengeListItemVo>>> {
        return apiLaunch(apiCall = { challengeApi.getMyChallenge() }, MyChallengeResponseMapper)
    }

    override suspend fun deleteChallenge(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.quitChallenge(request) }, UnitResponseMapper)
    }

    override suspend fun completeChallenge(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.completeChallenge(request) }, UnitResponseMapper)
    }
}