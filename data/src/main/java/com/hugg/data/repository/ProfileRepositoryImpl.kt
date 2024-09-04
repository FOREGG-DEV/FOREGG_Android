package com.hugg.data.repository

import com.hugg.data.api.ProfileApi
import com.hugg.data.base.BaseRepository
import com.hugg.data.mapper.UnitResponseMapper
import com.hugg.data.mapper.profile.MyMedicineInjectionResponseMapper
import com.hugg.data.mapper.profile.ProfileDetailResponseMapper
import com.hugg.domain.base.ApiState
import com.hugg.domain.model.request.profile.EditMyInfoRequestVo
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi
) : ProfileRepository, BaseRepository() {
    override suspend fun getMyInfo(): Flow<ApiState<ProfileDetailResponseVo>> {
        return apiLaunch(apiCall = { profileApi.getMyInfo() }, ProfileDetailResponseMapper )
    }

    override suspend fun editMyInfo(request: EditMyInfoRequestVo): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { profileApi.editMyInfo(request) }, UnitResponseMapper )
    }

    override suspend fun getMyMedicineInjection(request: String): Flow<ApiState<List<MyMedicineInjectionResponseVo>>> {
        return apiLaunch(apiCall = { profileApi.getMyMedicineInjectionInfo(request) }, MyMedicineInjectionResponseMapper )
    }

    override suspend fun logout(): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { profileApi.logout() }, UnitResponseMapper )
    }

    override suspend fun unRegister(): Flow<ApiState<Unit>> {
        return apiLaunch(apiCall = { profileApi.unRegister() }, UnitResponseMapper )
    }
}