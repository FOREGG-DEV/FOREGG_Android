package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.DailyRecordResponseVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyRecordUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<Unit, ApiState<DailyRecordResponseVo>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<DailyRecordResponseVo>> {
        return dailyRecordRepository.getDailyRecord()
    }
}