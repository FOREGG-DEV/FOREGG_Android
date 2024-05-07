package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleListUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<String, ApiState<List<ScheduleDetailVo>>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<List<ScheduleDetailVo>>> {
        return recordRepository.getScheduleList(request)
    }
}