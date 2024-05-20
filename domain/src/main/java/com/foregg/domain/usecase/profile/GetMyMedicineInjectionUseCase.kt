package com.foregg.domain.usecase.profile

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyMedicineInjectionUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
): UseCase<String, ApiState<List<MyMedicineInjectionResponseVo>>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<List<MyMedicineInjectionResponseVo>>> {
        return profileRepository.getMyMedicineInjection(request)
    }
}