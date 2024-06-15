package com.foregg.domain.usecase.information

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInformationByTypeUseCase @Inject constructor(
    private val informationRepository: InformationRepository
) : UseCase<InformationType, ApiState<List<InformationResponseVo>>>() {
    override suspend fun invoke(request: InformationType): Flow<ApiState<List<InformationResponseVo>>> {
        return informationRepository.getAllInformationByType(request)
    }
}