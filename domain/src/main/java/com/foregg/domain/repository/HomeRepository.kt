package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.HomeResponseVo
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getHome() : Flow<ApiState<HomeResponseVo>>
}