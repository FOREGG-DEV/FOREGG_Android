package com.foregg.presentation.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.foregg.domain.model.enums.NotificationType
import com.foregg.presentation.R
import com.foregg.presentation.ui.sign.SignActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val Context.dataStore by preferencesDataStore(name = "foregg_prefs")

class FcmNotification : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "notification_remote_channel"
        const val CHANNEL_NAME = "notification_channel_name"
        const val TITLE = "title"
        const val BODY = "body"
        const val TYPE = "type"
        const val TARGET_ID = "targetId"
        const val TIME = "time"
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
            NotificationType.INJECTION_FEMALE -> setAlarm(message.data)
            NotificationType.INJECTION_MALE,
            NotificationType.TODAY_RECORD_FEMALE,
            NotificationType.TODAY_RECORD_MALE -> sendNotification(message.data)
            NotificationType.CALENDAR -> saveFlags(PreferenceKeys.KEY_CALENDAR, type)
            NotificationType.LEDGER -> saveFlags(PreferenceKeys.KEY_LEDGER, type)
        }
    }
    private fun sendNotification(data: Map<String, String>) {
        val title = data[TITLE] ?: ""
        val body = data[BODY] ?: ""
        val type = NotificationType.valuesOf(data[TYPE] ?: "")
        val targetId = data[TARGET_ID]?.toLong()
        val time = data[TIME] ?: ""

        createNotificationChannel()
        val pendingIntent = getPendingIntent(type, targetId, time)

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else NotificationCompat.Builder(this)

        builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
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

    private fun setAlarm(data: Map<String, String>) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent().also { intent ->
                    intent.action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    startActivity(intent)
                }
            }
        }
        val intent = Intent(applicationContext, AlarmReceiver::class.java).apply {
            putExtra(TITLE, data[TITLE])
            putExtra(BODY, data[BODY])
            putExtra(TARGET_ID, data[TARGET_ID]?.toLong())
            putExtra(TIME, data[TIME])
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent)
    }

    private fun getPendingIntent(type: NotificationType, targetId : Long?, time : String) : PendingIntent{
        val intent = Intent(applicationContext, SignActivity::class.java).apply {
            when(type){
                NotificationType.INJECTION_FEMALE,
                NotificationType.INJECTION_MALE -> {
                    putExtra(PendingExtraValue.KEY, PendingExtraValue.INJECTION)
                    putExtra(PendingExtraValue.TARGET_ID_KEY, targetId)
                    putExtra(PendingExtraValue.INJECTION_TIME_KEY, time)
                }
                NotificationType.TODAY_RECORD_FEMALE -> putExtra(PendingExtraValue.KEY, PendingExtraValue.TODAY_RECORD_FEMALE)
                NotificationType.TODAY_RECORD_MALE -> putExtra(PendingExtraValue.KEY, PendingExtraValue.TODAY_RECORD_MALE)
                else -> {}
            }
        }
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
}