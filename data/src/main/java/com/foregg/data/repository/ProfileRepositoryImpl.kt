package com.foregg.data.repository

import com.foregg.data.api.ProfileApi
import com.foregg.data.base.BaseRepository
import com.foregg.data.mapper.profile.ProfileDetailResponseMapper
import com.foregg.domain.base.ApiState
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
}