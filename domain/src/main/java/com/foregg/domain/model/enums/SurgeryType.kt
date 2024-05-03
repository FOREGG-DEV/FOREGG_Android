package com.foregg.domain.model.enums

enum class SurgeryType(val type : String) {
    THINK_SURGERY("시술 고민 중"), EGG_FREEZING("난자 동결"), IN_VITRO_FERTILIZATION("체외 수정 (시험관 아기)");

    companion object {
        fun valuesOf(value: String): SurgeryType {
            return SurgeryType.values().find {
                it.type == value
            } ?: SurgeryType.THINK_SURGERY
        }
    }
}