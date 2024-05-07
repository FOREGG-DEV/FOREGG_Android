package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleDetailUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<Long, ApiState<ScheduleDetailVo>>() {
    override suspend operator fun invoke(request: Long): Flow<ApiState<ScheduleDetailVo>> {
        return recordRepository.getDetailRecord(request)
    }
}