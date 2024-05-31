package com.foregg.domain.usecase.dailyRecord

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.repository.DailyRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostShareInjectionUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
): UseCase<Unit, ApiState<Unit>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<Unit>> {
        return dailyRecordRepository.postShareInjection()
    }
}