package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.response.ChallengeCardVo
import com.hugg.domain.model.response.MyChallengeListItemVo
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getAllChallenge(): Flow<ApiState<List<ChallengeCardVo>>>
    suspend fun participateChallenge(request : Long) : Flow<ApiState<Unit>>
    suspend fun getMyChallenge(): Flow<ApiState<List<MyChallengeListItemVo>>>
    suspend fun deleteChallenge(request: Long) : Flow<ApiState<Unit>>
    suspend fun completeChallenge(request: Long) : Flow<ApiState<Unit>>
    suspend fun deleteCompleteChallenge(request: Long) : Flow<ApiState<Unit>>
    suspend fun markVisit(id : Long, time : String) : Flow<Boolean>
    suspend fun getVisitWeek(id : Long) : Flow<String>
    suspend fun removeVisitId(id : Long) : Flow<Boolean>
}