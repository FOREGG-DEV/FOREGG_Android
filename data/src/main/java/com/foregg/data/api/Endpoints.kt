package com.foregg.data.api

object Endpoints {

    object AUTH{
        private const val AUTH = "/auth"
        const val LOGIN = "$AUTH/login"
        const val JOIN = "$AUTH/join"
        const val GET_SHARE_CODE = "$AUTH/spouseCode"
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

    object Challenge {
        private const val CHALLENGE = "/challenge"
        const val ALL = "$CHALLENGE/all"
        const val PARTICIPATION = "$CHALLENGE/participation/{id}"
        const val QUIT = "$CHALLENGE/quit/{id}"
        const val COMPLETE = "$CHALLENGE/complete/{id}"
        const val MY = "$CHALLENGE/my"
    }

    object ACCOUNT{
        private const val LEDGER = "/ledger"
        const val GET_BY_CONDITION = "$LEDGER/byCondition"
        const val GET_BY_MONTH = "$LEDGER/byMonth"
        const val GET_BY_COUNT = "$LEDGER/byCount"
        const val DELETE = "$LEDGER/{id}/delete"
        const val MODIFY = "$LEDGER/{id}/modify"
        const val CREATE = "$LEDGER/add"
        const val GET_DETAIL = "$LEDGER/{id}/detail"
    }

    object PROFILE{
        const val MY_INFO = "/myPage"
        const val MODIFY = "$MY_INFO/modifySurgery"
    }
}