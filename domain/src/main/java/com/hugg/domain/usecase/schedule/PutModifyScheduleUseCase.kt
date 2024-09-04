package com.hugg.domain.usecase.schedule

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.ScheduleModifyRequestVo
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutModifyScheduleUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<ScheduleModifyRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: ScheduleModifyRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.modifySchedule(request.id, request.request)
    }
}