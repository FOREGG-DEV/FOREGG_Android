package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
): UseCase<String, ApiState<Unit>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<Unit>> {
        return authRepository.login(request)
    }
}