package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import kotlinx.coroutines.flow.Flow

interface InformationRepository {
    suspend fun getAllInformation() : Flow<ApiState<List<InformationResponseVo>>>
    suspend fun getAllInformationByType(request : InformationType) : Flow<ApiState<List<InformationResponseVo>>>
}