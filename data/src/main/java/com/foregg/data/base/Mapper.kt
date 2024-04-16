package com.foregg.data.base

interface Mapper {
    interface ResponseMapper<DTO, DOMAIN_MODEL>:Mapper {
        fun mapDtoToModel(type: DTO?):DOMAIN_MODEL
    }
}