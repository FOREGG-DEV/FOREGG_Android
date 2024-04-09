package com.foregg.data.base

import com.foregg.domain.base.DomainResponse


interface Mapper {
    interface ResponseMapper<DTO : DataDto, DOMAIN_MODEL : DomainResponse>:Mapper {
        fun mapDtoToModel(type: DTO?):DOMAIN_MODEL
    }
}