package com.foregg.domain.usecase.profile

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<Unit, ApiState<ProfileDetailResponseVo>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<ProfileDetailResponseVo>> {
        return profileRepository.getMyInfo()
    }
}