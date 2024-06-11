package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<Long, ApiState<Unit>>() {
    override suspend fun invoke(request: Long): Flow<ApiState<Unit>> {
        return dailyRecordRepository.deleteDailyRecord(request)
    }
}