package com.hugg.data.repository

import com.hugg.data.api.HomeApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.HomeResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.response.HomeResponseVo
import com.hugg.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository, BaseRepository() {
    override suspend fun getHome(): Flow<ApiState<HomeResponseVo>> {
        return apiLaunch(apiCall = { homeApi.getHome() }, HomeResponseMapper)
    }
}