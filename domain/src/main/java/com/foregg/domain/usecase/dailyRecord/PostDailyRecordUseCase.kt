package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<CreateDailyRecordRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: CreateDailyRecordRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.createDailyRecord(request)
    }
}