package com.hugg.domain.usecase.auth

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUnregisterUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<Unit, ApiState<Unit>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<Unit>> {
        return profileRepository.unRegister()
    }
}