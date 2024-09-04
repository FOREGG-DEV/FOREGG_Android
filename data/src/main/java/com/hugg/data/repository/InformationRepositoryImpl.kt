package com.hugg.data.repository

import com.hugg.data.api.InformationApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.information.InformationResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.enums.InformationType
import com.hugg.domain.model.response.information.InformationResponseVo
import com.hugg.domain.repository.InformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InformationRepositoryImpl @Inject constructor(
    private val informationApi: InformationApi
) : InformationRepository, BaseRepository() {

    override suspend fun getAllInformation(): Flow<ApiState<List<InformationResponseVo>>> {
        return apiLaunch(apiCall = { informationApi.getAllInformation() }, InformationResponseMapper)
    }

    override suspend fun getAllInformationByType(request: InformationType): Flow<ApiState<List<InformationResponseVo>>> {
        return apiLaunch(apiCall = { informationApi.getInformationByType(request) }, InformationResponseMapper)
    }
}