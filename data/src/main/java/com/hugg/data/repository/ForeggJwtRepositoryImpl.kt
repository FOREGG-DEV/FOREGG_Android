package com.hugg.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hugg.data.api.ForeggJwtTokenApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.ForeggJwtResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.response.ForeggJwtResponseVo
import com.hugg.domain.repository.ForeggJwtRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForeggJwtRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val plubJwtTokenApi: ForeggJwtTokenApi,
) : ForeggJwtRepository, BaseRepository() {
    private companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    private val Context.tokenDataStore by preferencesDataStore("foregg_data_store")
    override suspend fun saveAccessTokenAndRefreshToken(request: SaveForeggJwtRequestVo): Flow<Boolean> = flow {
        request.run {
            context.tokenDataStore.edit { prefs ->
                prefs[ACCESS_TOKEN_KEY] = request.accessToken
                prefs[REFRESH_TOKEN_KEY] = request.refreshToken
            }
            emit(true)
        }
    }.catch { emit(false) }

    override fun getAccessToken(): Flow<String> {
        return context.tokenDataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]?.toString() ?: ""
        }
    }

    override fun getRefreshToken(): Flow<String> {
        return context.tokenDataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]?.toString() ?: ""
        }
    }

    override suspend fun reIssueToken(request: String): Flow<ApiState<ForeggJwtResponseVo>> {
        return apiLaunch(apiCall = { plubJwtTokenApi.reIssueToken(request) }, ForeggJwtResponseMapper)
    }
}