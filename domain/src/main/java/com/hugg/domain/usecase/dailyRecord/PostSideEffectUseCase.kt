package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<CreateSideEffectRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: CreateSideEffectRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.createSideEffect(request)
    }
}