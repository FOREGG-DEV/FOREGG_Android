package com.hugg.domain.usecase.information

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.enums.InformationType
import com.hugg.domain.model.response.information.InformationResponseVo
import com.hugg.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInformationByTypeUseCase @Inject constructor(
    private val informationRepository: InformationRepository
) : UseCase<InformationType, ApiState<List<InformationResponseVo>>>() {
    override suspend fun invoke(request: InformationType): Flow<ApiState<List<InformationResponseVo>>> {
        return informationRepository.getAllInformationByType(request)
    }
}