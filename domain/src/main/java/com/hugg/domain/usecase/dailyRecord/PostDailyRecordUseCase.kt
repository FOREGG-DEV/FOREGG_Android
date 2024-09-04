package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.dailyRecord.CreateDailyRecordRequestVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<CreateDailyRecordRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: CreateDailyRecordRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.createDailyRecord(request)
    }
}