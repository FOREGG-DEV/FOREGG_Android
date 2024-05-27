package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.SideEffectListItemVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<Unit, ApiState<List<SideEffectListItemVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<SideEffectListItemVo>>> {
        return dailyRecordRepository.getSideEffect()
    }
}