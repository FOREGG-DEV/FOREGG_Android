package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getMyInfo() : Flow<ApiState<ProfileDetailResponseVo>>
}