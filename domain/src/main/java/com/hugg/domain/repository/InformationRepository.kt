package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.enums.InformationType
import com.hugg.domain.model.response.information.InformationResponseVo
import kotlinx.coroutines.flow.Flow

interface InformationRepository {
    suspend fun getAllInformation() : Flow<ApiState<List<InformationResponseVo>>>
    suspend fun getAllInformationByType(request : InformationType) : Flow<ApiState<List<InformationResponseVo>>>
}