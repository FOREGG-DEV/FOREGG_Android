package com.foregg.domain.model.enums

enum class RepeatDayType(val week : String) {
    EVERY("매일"), MON("월"), TUE("화"), WED("수"),
    THU("목"), FRI("금"), SAT("토"), SUN("일");

    companion object {
        fun valuesOf(value: String): RepeatDayType {
            return RepeatDayType.values().find {
                it.week == value
            } ?: RepeatDayType.MON
        }
    }
}