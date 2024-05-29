package com.foregg.data.base

object StatusCode {
    const val SUCCESS = "COMMON200"
    const val ERROR = "ERROR400"

    object AUTH {
        const val USER_NEED_JOIN = "USER4002"
        const val NOT_CORRECT_SHARE_CODE = "USER4004"
    }

    object RECORD {
        const val NO_EXIST_SCHEDULE = "RECORD4001"
    }
}