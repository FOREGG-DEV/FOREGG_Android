package com.hugg.domain.usecase.profile

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyMedicineInjectionUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<String, ApiState<List<MyMedicineInjectionResponseVo>>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<List<MyMedicineInjectionResponseVo>>> {
        return profileRepository.getMyMedicineInjection(request)
    }
}