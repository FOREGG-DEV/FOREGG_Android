package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.dailyRecord.SideEffectEditRequestVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<SideEffectEditRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: SideEffectEditRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.editSideEffect(request)
    }
}