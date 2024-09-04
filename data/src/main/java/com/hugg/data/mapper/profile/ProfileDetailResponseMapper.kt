package com.hugg.data.mapper.profile

import com.hugg.data.base.Mapper
import com.hugg.data.dto.profile.ProfileDetailResponse
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo

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
                ssn = ssn,
                shareCode = spouseCode
            )
        }?: ProfileDetailResponseVo()
    }
}