package com.foregg.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.foregg.domain.base.ApiState
import com.foregg.domain.model.enums.NotificationType
import com.foregg.domain.model.request.fcm.RenewalFcmRequestVo
import com.foregg.domain.usecase.auth.PostRenewalFcmUseCase
import com.foregg.presentation.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(name = "foregg_prefs")

class FcmNotification : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "notification_remote_channel"
        const val CHANNEL_NAME = "notification_channel_name"
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val TYPE = "type"
        private const val TARGET_ID = "targetId"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.data.isEmpty()) return
        val type = NotificationType.valuesOf(message.data[TYPE] ?: "")
        when(type){
            NotificationType.INJECTION_FEMALE -> {} //sendNotification(message.data)
            NotificationType.INJECTION_MALE,
            NotificationType.TODAY_RECORD_FEMALE -> sendNotification(message.data)
            NotificationType.TODAY_RECORD_MALE -> {
                sendNotification(message.data)
                saveFlags(PreferenceKeys.KEY_TODAY_RECORD, type)
            }
            NotificationType.CALENDAR -> saveFlags(PreferenceKeys.KEY_CALENDAR, type)
            NotificationType.LEDGER -> saveFlags(PreferenceKeys.KEY_LEDGER, type)
        }
    }
    private fun sendNotification(data: Map<String, String>) {
        val title = data[TITLE] ?: ""
        val body = data[BODY] ?: ""

        createNotificationChannel()

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else NotificationCompat.Builder(this)

        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        } else return

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.createNotificationChannel(channel)
    }


    private fun saveFlags(key : Preferences.Key<Boolean>, type: NotificationType) {
        CoroutineScope(Dispatchers.IO).launch {
            applicationContext.dataStore.edit { preferences ->
                preferences[key] = true
            }
            ForeggNotification.updateNoty(applicationContext, type, true)
        }
    }
}