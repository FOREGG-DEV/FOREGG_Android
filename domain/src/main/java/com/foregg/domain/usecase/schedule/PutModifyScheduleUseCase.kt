package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.ScheduleModifyRequestVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutModifyScheduleUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<ScheduleModifyRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: ScheduleModifyRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.modifySchedule(request.id, request.request)
    }
}