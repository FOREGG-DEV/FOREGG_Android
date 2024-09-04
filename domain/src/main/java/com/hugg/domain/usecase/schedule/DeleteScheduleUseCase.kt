package com.hugg.domain.usecase.schedule

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<Long, ApiState<Unit>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<Unit>> {
        return recordRepository.deleteSchedule(request)
    }
}