package com.foregg.domain.usecase.home

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : UseCase<Unit, ApiState<HomeResponseVo>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<HomeResponseVo>> {
        return homeRepository.getHome()
    }
}