package com.foregg.data.mapper.profile

import com.foregg.data.base.Mapper
import com.foregg.data.dto.profile.ProfileDetailResponse
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo

object ProfileDetailResponseMapper: Mapper.ResponseMapper<ProfileDetailResponse, ProfileDetailResponseVo> {

    override fun mapDtoToModel(type: ProfileDetailResponse?): ProfileDetailResponseVo {
        return type?.run {
            ProfileDetailResponseVo(
                nickName = nickname,
                surgeryType = surgeryType,
                round = count ?: 0,
                startDate = startDate ?: "",
                spouse = spouse ?: "",
                genderType = if(ssn.split("-")[1].toInt() % 2 == 0) GenderType.FEMALE else GenderType.MALE,
                ssn = ssn
            )
        }?: ProfileDetailResponseVo()
    }
}