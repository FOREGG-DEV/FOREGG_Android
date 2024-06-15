package com.foregg.data.repository

import com.foregg.data.api.InformationApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.information.InformationResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.repository.InformationRepository
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