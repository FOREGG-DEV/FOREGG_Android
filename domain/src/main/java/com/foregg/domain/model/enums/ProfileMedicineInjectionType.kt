package com.foregg.domain.model.enums

enum class ProfileMedicineInjectionType(val type : String) {
    MEDICINE("medicine"), INJECTION("injection");

    companion object {
        fun valuesOf(value: String): ProfileMedicineInjectionType {
            return ProfileMedicineInjectionType.values().find {
                it.type == value
            } ?: ProfileMedicineInjectionType.MEDICINE
        }
    }
}