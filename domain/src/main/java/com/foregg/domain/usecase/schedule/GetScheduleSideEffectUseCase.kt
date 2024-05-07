package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.vo.MedicalRecord
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleSideEffectUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<Long, ApiState<MedicalRecord>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<MedicalRecord>> {
        return recordRepository.getMedicalRecordAndSideEffect(request)
    }
}