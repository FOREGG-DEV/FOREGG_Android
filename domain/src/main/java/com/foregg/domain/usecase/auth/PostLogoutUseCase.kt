package com.foregg.domain.usecase.auth

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<Unit, ApiState<Unit>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<Unit>> {
        return profileRepository.logout()
    }
}