package com.foregg.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.foregg.data.api.ForeggJwtTokenApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.ForeggJwtResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.ForeggJwtReIssueRequestVo
import com.foregg.domain.model.request.SaveForeggJwtRequestVo
import com.foregg.domain.model.response.ForeggJwtResponseVo
import com.foregg.domain.repository.ForeggJwtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForeggJwtRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val plubJwtTokenApi: ForeggJwtTokenApi,
) : ForeggJwtRepository, BaseRepository() {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
    override suspend fun saveAccessTokenAndRefreshToken(request: SaveForeggJwtRequestVo): Flow<Boolean> = flow {
        request.run {
            dataStore.edit { prefs ->
                prefs[ACCESS_TOKEN_KEY] = request.accessToken
                prefs[REFRESH_TOKEN_KEY] = request.refreshToken
            }
            emit(true)
        }
    }.catch { emit(false) }

    override fun getAccessToken(): Flow<String> {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]?.toString() ?: ""
        }
    }

    override fun getRefreshToken(): Flow<String> {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]?.toString() ?: ""
        }
    }

    override suspend fun reIssueToken(request: ForeggJwtReIssueRequestVo): Flow<ApiState<ForeggJwtResponseVo>> {
        return apiLaunch(apiCall = { plubJwtTokenApi.reIssueToken(request) }, ForeggJwtResponseMapper)
    }
}