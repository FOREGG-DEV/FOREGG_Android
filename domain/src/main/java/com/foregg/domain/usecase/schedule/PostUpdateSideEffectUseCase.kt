package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.AddMedicalRecordRequestVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUpdateSideEffectUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<AddMedicalRecordRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: AddMedicalRecordRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.addMedicalRecord(request.id, request.request)
    }
}