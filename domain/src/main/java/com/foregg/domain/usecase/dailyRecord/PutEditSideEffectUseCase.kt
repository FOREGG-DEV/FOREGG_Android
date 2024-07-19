package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.SideEffectEditRequestVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<SideEffectEditRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: SideEffectEditRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.editSideEffect(request)
    }
}