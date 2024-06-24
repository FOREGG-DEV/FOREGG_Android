package com.foregg.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.foregg.data.api.ChallengeApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.data.mapper.challenge.ChallengeResponseMapper
import com.foregg.data.mapper.challenge.MyChallengeResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.ChallengeCardVo
import com.foregg.domain.model.response.MyChallengeListItemVo
import com.foregg.domain.repository.ChallengeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val challengeApi: ChallengeApi
) : ChallengeRepository, BaseRepository() {

    companion object {
        const val LAST_VIEWED = "lastViewed_"
    }

    private val Context.dataStore by preferencesDataStore(name = "foregg_challenge_data_store")
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

    override suspend fun deleteCompleteChallenge(request: Long): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { challengeApi.deleteCompleteChallenge(request) }, UnitResponseMapper)
    }

    override suspend fun markVisit(id : Long, time : String) = flow {
        val key = stringPreferencesKey("$LAST_VIEWED$id")
        run {
            context.dataStore.edit { preferences ->
                preferences[key] = time
            }
            emit(true)
        }
    }.catch { emit(false) }

    override suspend fun getVisitWeek(id : Long): Flow<String> {
        val key = stringPreferencesKey("$LAST_VIEWED$id")
        return context.dataStore.data.map { prefs ->
            prefs[key]?.toString() ?: ""
        }
    }

    override suspend fun removeVisitId(id: Long) : Flow<Boolean> = flow {
        val key = stringPreferencesKey("$LAST_VIEWED$id")
        run {
            context.dataStore.edit { preferences ->
                preferences.remove(key)
            }
            emit(true)
        }
    }.catch { emit(false) }
}