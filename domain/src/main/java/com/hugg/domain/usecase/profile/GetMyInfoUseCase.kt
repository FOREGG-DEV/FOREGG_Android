package com.hugg.domain.usecase.profile

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<Unit, ApiState<ProfileDetailResponseVo>>() {
    override suspend operator fun invoke(request: Unit): Flow<ApiState<ProfileDetailResponseVo>> {
        return profileRepository.getMyInfo()
    }
}