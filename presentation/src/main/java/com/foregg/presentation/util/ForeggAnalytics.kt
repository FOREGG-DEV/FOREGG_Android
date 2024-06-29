package com.foregg.presentation.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

object ForeggAnalytics {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    fun init(context: Context){
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(eventName : String, screenName : String, ){
        firebaseAnalytics.logEvent(eventName) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param("gendar_type", UserInfo.info.genderType.type)
        }
    }
}