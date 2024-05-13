package com.foregg.domain.usecase.profile

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditMyInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<EditMyInfoRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: EditMyInfoRequestVo): Flow<ApiState<Unit>> {
        return profileRepository.editMyInfo(request)
    }
}