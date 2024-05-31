package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.foregg.domain.model.response.daily.InjectionInfoResponseVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInjectionInfoUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<InjectionAlarmRequestVo, ApiState<InjectionInfoResponseVo>>() {
    override suspend fun invoke(request: InjectionAlarmRequestVo): Flow<ApiState<InjectionInfoResponseVo>> {
        return dailyRecordRepository.getInjectionInfo(request)
    }
}