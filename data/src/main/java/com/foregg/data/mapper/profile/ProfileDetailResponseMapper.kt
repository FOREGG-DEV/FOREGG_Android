package com.foregg.data.mapper.profile

import com.foregg.data.base.Mapper
import com.foregg.data.dto.profile.ProfileDetailResponse
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo

object ProfileDetailResponseMapper: Mapper.ResponseMapper<ProfileDetailResponse, ProfileDetailResponseVo> {

    override fun mapDtoToModel(type: ProfileDetailResponse?): ProfileDetailResponseVo {
        return type?.run {
            ProfileDetailResponseVo(
                nickName = nickname,
                surgeryType = surgeryType,
                round = count ?: 0,
                startDate = startDate ?: "",
                spouse = spouse ?: ""
            )
        }?: ProfileDetailResponseVo()
    }
}