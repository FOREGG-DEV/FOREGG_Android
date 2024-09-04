package com.hugg.domain.usecase.profile

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.profile.EditMyInfoRequestVo
import com.hugg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditMyInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<EditMyInfoRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: EditMyInfoRequestVo): Flow<ApiState<Unit>> {
        return profileRepository.editMyInfo(request)
    }
}