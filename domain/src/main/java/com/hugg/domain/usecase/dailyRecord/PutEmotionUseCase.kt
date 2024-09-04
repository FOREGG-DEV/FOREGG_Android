package com.hugg.domain.usecase.dailyRecord

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.request.dailyRecord.PutEmotionVo
import com.hugg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEmotionUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<PutEmotionVo, ApiState<Unit>>() {
    override suspend fun invoke(request: PutEmotionVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.putEmotion(request)
    }
}