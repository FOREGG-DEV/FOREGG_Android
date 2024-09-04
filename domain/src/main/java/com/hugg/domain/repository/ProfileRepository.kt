package com.hugg.domain.repository

import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.profile.EditMyInfoRequestVo
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getMyInfo() : Flow<ApiState<ProfileDetailResponseVo>>
    suspend fun editMyInfo(request: EditMyInfoRequestVo) : Flow<ApiState<Unit>>
    suspend fun getMyMedicineInjection(request : String) : Flow<ApiState<List<MyMedicineInjectionResponseVo>>>
    suspend fun logout() : Flow<ApiState<Unit>>
    suspend fun unRegister() : Flow<ApiState<Unit>>
}