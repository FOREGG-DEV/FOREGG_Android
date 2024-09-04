package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.SideEffectListItemVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<Unit, ApiState<List<SideEffectListItemVo>>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<List<SideEffectListItemVo>>> {
        return dailyRecordRepository.getSideEffect()
    }
}