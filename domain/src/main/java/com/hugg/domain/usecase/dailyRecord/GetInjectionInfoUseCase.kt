package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInjectionInfoUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<InjectionAlarmRequestVo, ApiState<InjectionInfoResponseVo>>() {
    override suspend fun invoke(request: InjectionAlarmRequestVo): Flow<ApiState<InjectionInfoResponseVo>> {
        return dailyRecordRepository.getInjectionInfo(request)
    }
}