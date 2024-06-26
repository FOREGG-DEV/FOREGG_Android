package com.foregg.data.repository

import com.foregg.data.api.ProfileApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.UnitResponseMapper
import com.foregg.data.mapper.profile.MyMedicineInjectionResponseMapper
import com.foregg.data.mapper.profile.ProfileDetailResponseMapper
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import com.foregg.domain.repository.ProfileRepository
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