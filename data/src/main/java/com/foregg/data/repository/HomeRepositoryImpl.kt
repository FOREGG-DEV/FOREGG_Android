package com.foregg.data.repository

import com.foregg.data.api.HomeApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.HomeResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.HomeResponseVo
import com.foregg.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository, BaseRepository() {
    override suspend fun getHome(): Flow<ApiState<HomeResponseVo>> {
        return apiLaunch(apiCall = { homeApi.getHome() }, HomeResponseMapper)
    }
}