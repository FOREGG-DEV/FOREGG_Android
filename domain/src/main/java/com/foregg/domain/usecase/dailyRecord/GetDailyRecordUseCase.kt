package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.DailyRecordResponseVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<Unit, ApiState<DailyRecordResponseVo>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<DailyRecordResponseVo>> {
        return dailyRecordRepository.getDailyRecord()
    }
}