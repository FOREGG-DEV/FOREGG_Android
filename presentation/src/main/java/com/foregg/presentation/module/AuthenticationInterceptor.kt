package com.foregg.presentation.module

import android.util.Log
import com.foregg.domain.usecase.jwtToken.GetForeggAccessTokenUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor@Inject constructor(
    private val getForeggAccessTokenUseCase: GetForeggAccessTokenUseCase,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = runBlocking { getForeggAccessTokenUseCase(Unit).first() }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken").build()

        Log.d("RETROFIT",
            "AuthenticationInterceptor - intercept() called / request header: ${request.headers}"
        )
        return chain.proceed(request)
    }
}