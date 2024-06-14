package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getAllChallenge(): Flow<ApiState<List<ChallengeCardVo>>>
    suspend fun participateChallenge(request : Long) : Flow<ApiState<Unit>>
    suspend fun getMyChallenge(): Flow<ApiState<List<MyChallengeListItemVo>>>
    suspend fun deleteChallenge(request: Long) : Flow<ApiState<Unit>>
    suspend fun completeChallenge(request: Long) : Flow<ApiState<Unit>>
    suspend fun deleteCompleteChallenge(request: Long) : Flow<ApiState<Unit>>
}