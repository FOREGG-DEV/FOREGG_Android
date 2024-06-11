package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.EditDailyRecordRequestVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<EditDailyRecordRequestVo, ApiState<Unit>>() {
    override suspend fun invoke(request: EditDailyRecordRequestVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.editDailyRecord(request)
    }
}