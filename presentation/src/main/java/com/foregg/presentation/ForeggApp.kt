package com.foregg.presentation

import android.app.Application
import com.foregg.presentation.util.ForeggAnalytics
import com.foregg.presentation.util.ForeggNotification
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class ForeggApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        CoroutineScope(Dispatchers.IO).launch {
            ForeggAnalytics.init(applicationContext)
            ForeggNotification.init(applicationContext)
        }
        FirebaseApp.initializeApp(this);
    }
}