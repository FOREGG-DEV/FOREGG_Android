package com.foregg.data.api

object Endpoints {

    object AUTH{
        private const val AUTH = "/auth"
        const val LOGIN = "$AUTH/login"
        const val JOIN = "$AUTH/join"
    }

    object RECORD {
        private const val RECORD = "/record"
        const val MODIFY = "$RECORD/{id}/modify"
        const val MEDICAL_RECORD = "$RECORD/{id}/medicalRecord"
        const val ADD = "$RECORD/add"
        const val MEDICAL_RECORD_AND_SIDE_EFFECT = "$RECORD/{id}/medicalRecordAndSideEffect"
        const val DELETE = "$RECORD/{id}/delete"
        const val DETAIL = "$RECORD/{id}/detail"
        const val SCHEDULE_LIST = "schedule"
    }

    object Home {
        const val HOME = "/home"
    }

    object DailyRecord {
        const val DAILY = "/daily"
        const val WRITE = "/write"
    }

    object ACCOUNT{
        private const val LEDGER = "/ledger"
        const val GET_BY_CONDITION = "$LEDGER/byCondition"
        const val GET_BY_MONTH = "$LEDGER/byMonth"
        const val GET_BY_COUNT = "$LEDGER/byCount"
        const val DELETE = "$LEDGER/{id}/delete"
    }
}