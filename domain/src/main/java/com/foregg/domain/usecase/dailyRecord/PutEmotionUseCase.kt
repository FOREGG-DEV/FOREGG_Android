package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.dailyRecord.PutEmotionVo
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEmotionUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : UseCase<PutEmotionVo, ApiState<Unit>>() {
    override suspend fun invoke(request: PutEmotionVo): Flow<ApiState<Unit>> {
        return dailyRecordRepository.putEmotion(request)
    }
}