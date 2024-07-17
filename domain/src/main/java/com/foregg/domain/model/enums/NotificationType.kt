package com.foregg.domain.model.enums

enum class NotificationType(val type : String) {
    INJECTION_FEMALE("INJECTION_FEMALE"), INJECTION_MALE("INJECTION_MALEe"),
    TODAY_RECORD_FEMALE("TODAY_RECORD_FEMALE"), TODAY_RECORD_MALE("TODAY_RECORD_MALE"),
    CALENDAR("CALENDAR"), LEDGER("LEDGER");

    companion object {
        fun valuesOf(value: String): NotificationType {
            return NotificationType.values().find {
                it.type == value
            } ?: NotificationType.INJECTION_FEMALE
        }
    }
}