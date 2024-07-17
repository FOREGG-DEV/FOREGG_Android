package com.foregg.domain.model.enums

enum class NotificationType(val type : String) {
    INJECTION_FEMALE("injection female"), INJECTION_MALE("injection male"),
    TODAY_RECORD_FEMALE("today record female"), TODAY_RECORD_MALE("today record male"),
    CALENDAR("calendar"), LEDGER("ledger");

    companion object {
        fun valuesOf(value: String): NotificationType {
            return NotificationType.values().find {
                it.type == value
            } ?: NotificationType.INJECTION_FEMALE
        }
    }
}