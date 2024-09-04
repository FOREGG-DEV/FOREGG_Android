package com.hugg.domain.usecase.home

import com.hugg.domain.base.ApiState
import com.hugg.domain.base.UseCase
import com.hugg.domain.model.response.HomeResponseVo
import com.hugg.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : UseCase<Unit, ApiState<HomeResponseVo>>() {
    override suspend fun invoke(request: Unit): Flow<ApiState<HomeResponseVo>> {
        return homeRepository.getHome()
    }
}