package com.foregg.presentation

import android.app.Application
import com.foregg.presentation.util.KAKAO_NATIVE_KEY
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ForeggApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
    }
}