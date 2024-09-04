package com.hugg.domain.usecase.schedule

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.vo.ScheduleDetailVo
import com.hugg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleListUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<String, ApiState<List<ScheduleDetailVo>>>() {
    override suspend operator fun invoke(request: String): Flow<ApiState<List<ScheduleDetailVo>>> {
        return recordRepository.getScheduleList(request)
    }
}