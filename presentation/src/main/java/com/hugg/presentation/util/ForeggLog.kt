package com.hugg.presentation.util

import android.util.Log

object ForeggLog {

    fun D(msg : String) {
        Log.d("FOREGG LOG", msg)
    }

    fun D(log : String, msg : String) {
        Log.d(log, msg)
    }
}