package com.hugg.domain.usecase.schedule

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.ScheduleDetailRequestVo
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostAddScheduleUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<ScheduleDetailRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: ScheduleDetailRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.addSchedule(request)
    }
}