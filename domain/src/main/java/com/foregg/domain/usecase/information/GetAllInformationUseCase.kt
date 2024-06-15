package com.foregg.domain.usecase.information

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInformationUseCase @Inject constructor(
    private val informationRepository: InformationRepository
) : UseCase<Unit, ApiState<List<InformationResponseVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<InformationResponseVo>>> {
        return informationRepository.getAllInformation()
    }
}