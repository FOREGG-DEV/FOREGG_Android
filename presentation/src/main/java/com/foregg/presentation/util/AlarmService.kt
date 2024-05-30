package com.foregg.presentation.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.foregg.presentation.R
import com.foregg.presentation.ui.sign.SignActivity

class AlarmService : Service() {

    companion object{
        const val STOP_ALARM = "STOP_ALARM"
        const val CHANNEL_ID = "alarm_service_channel"
        var ringtone: Ringtone? = null
        var vibrator: Vibrator? = null

        fun stopAlarm() {
            ringtone?.stop()
            vibrator?.cancel()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == STOP_ALARM) {
            stopAlarm()
            stopSelf()
            return START_NOT_STICKY
        }
        val title = intent?.getStringExtra(FcmNotification.TITLE) ?: ""
        val body = intent?.getStringExtra(FcmNotification.BODY) ?: ""
        val targetId = intent?.getLongExtra(FcmNotification.TARGET_ID, -1) ?: 0

        createNotificationChannel()

        val mainIntent = Intent(applicationContext, SignActivity::class.java).apply {
            putExtra(STOP_ALARM, true)
            putExtra(PendingExtraValue.KEY, PendingExtraValue.INJECTION)
            putExtra(PendingExtraValue.TARGET_ID_KEY, targetId)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, AlarmService::class.java).apply { action = STOP_ALARM }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        // 커스텀 알림 뷰 설정
//        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification).apply {
//            setTextViewText(R.id.title, title)
//            setTextViewText(R.id.text, body)
//            setOnClickPendingIntent(R.id.stop_alarm_button, stopPendingIntent)
//        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setDeleteIntent(stopPendingIntent)
            .build()

        checkPermission()
        startForeground(1, notificationBuilder)

        notificationManager.notify(1, notificationBuilder)
        startAlarm()
        return START_STICKY
    }

    private fun startAlarm(){
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 1000, 1000)  // 대기, 진동, 대기
        vibrator?.vibrate(pattern, 1)

        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone?.play()
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Alarm Service Channel", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
