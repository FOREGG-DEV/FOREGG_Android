package com.foregg.presentation.module

import android.util.Log
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.ForeggJwtReIssueRequestVo
import com.foregg.domain.model.request.SaveForeggJwtRequestVo
import com.foregg.domain.model.response.ForeggJwtResponseVo
import com.foregg.domain.usecase.jwtToken.GetForeggAccessTokenUseCase
import com.foregg.domain.usecase.jwtToken.GetFroeggRefreshTokenUseCase
import com.foregg.domain.usecase.jwtToken.PostReIssueTokenUseCase
import com.foregg.domain.usecase.jwtToken.SaveForeggAccessTokenAndRefreshTokenUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val getForeggAccessTokenUseCase: GetForeggAccessTokenUseCase,
    private val getFroeggRefreshTokenUseCase: GetFroeggRefreshTokenUseCase,
    private val saveForeggAccessTokenAndRefreshTokenUseCase: SaveForeggAccessTokenAndRefreshTokenUseCase,
    private val postReIssueTokenUseCase: PostReIssueTokenUseCase
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? = runBlocking {
        val access = async { getForeggAccessTokenUseCase(Unit).first() }
        val refresh = async { getFroeggRefreshTokenUseCase(Unit).first() }
        val accessToken = access.await()
        val refreshToken = refresh.await()

        mutex.withLock {
            if (verifyTokenIsRefreshed(accessToken, refreshToken)) {
                Log.d("RETROFIT","TokenAuthenticator - authenticate() called / 중단된 API 재요청")
                response.request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .header(
                        "Authorization",
                        "Bearer " + getForeggAccessTokenUseCase(Unit).first()
                    )
                    .build()
            } else null
        }
    }

    private suspend fun verifyTokenIsRefreshed(
        access: String,
        refresh: String
    ): Boolean {
        val newAccess = getForeggAccessTokenUseCase(Unit).first()

        return if (access != newAccess) true else {
            Log.d("RETROFIT","TokenAuthenticator - authenticate() called / 토큰 만료. 토큰 Refresh 요청: $refresh")
            val reIssueRequestVo = ForeggJwtReIssueRequestVo(refresh)
            var foreggJwtToken = ForeggJwtResponseVo("", "")
            postReIssueTokenUseCase(reIssueRequestVo).collect { state ->
                when(state) {
                    is ApiState.Loading -> { }
                    is ApiState.Success -> {
                        foreggJwtToken = state.data
                        return@collect
                    }
                    else -> {
                        return@collect
                    }
                }
            }

            val savePlubJwtRequestVo = SaveForeggJwtRequestVo(foreggJwtToken.accessToken, foreggJwtToken.refreshToken)

            saveForeggAccessTokenAndRefreshTokenUseCase(savePlubJwtRequestVo).first()
            foreggJwtToken.isTokenValid.apply {
                if(!this) Log.d("RETROFIT","TokenAuthenticator - verifyTokenIsRefreshed() called / 토큰 갱신 실패.")
            }
        }
    }
}