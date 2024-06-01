package com.foregg.presentation.util

import android.content.*
import android.os.Build

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(FcmNotification.TITLE, intent.getStringExtra(FcmNotification.TITLE))
            putExtra(FcmNotification.BODY, intent.getStringExtra(FcmNotification.BODY))
            putExtra(FcmNotification.TARGET_ID, intent.getLongExtra(FcmNotification.TARGET_ID, 0))
            putExtra(FcmNotification.TIME, intent.getStringExtra(FcmNotification.TIME))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}
