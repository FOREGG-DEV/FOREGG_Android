package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.CreateSideEffectRequestVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostSideEffectUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<CreateSideEffectRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: CreateSideEffectRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.createSideEffect(request)
    }
}