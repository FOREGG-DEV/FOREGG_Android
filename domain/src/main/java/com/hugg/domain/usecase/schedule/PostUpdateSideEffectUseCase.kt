package com.hugg.domain.usecase.schedule

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.AddMedicalRecordRequestVo
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUpdateSideEffectUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<AddMedicalRecordRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AddMedicalRecordRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.addMedicalRecord(request.id, request.request)
    }
}