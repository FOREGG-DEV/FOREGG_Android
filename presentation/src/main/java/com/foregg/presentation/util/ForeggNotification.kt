package com.foregg.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.foregg.domain.model.enums.NotificationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object ForeggNotification {
    val calendarNotificationStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val ledgerNotificationStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    suspend fun init(context: Context) {
        val preferences = context.dataStore.data.first()
        calendarNotificationStateFlow.update { preferences[PreferenceKeys.KEY_CALENDAR] ?: false }
        ledgerNotificationStateFlow.update { preferences[PreferenceKeys.KEY_LEDGER] ?: false }
    }

    fun updateNoty(context: Context, type : NotificationType, value : Boolean) {
        var key = PreferenceKeys.KEY_NOTHING
        when(type){
            NotificationType.CALENDAR -> {
                updateCalendarNotification(value)
                key = PreferenceKeys.KEY_CALENDAR
            }
            NotificationType.LEDGER -> {
                updateLedgerNotification(value)
                key = PreferenceKeys.KEY_LEDGER
            }
            else -> {}
        }
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    private fun updateCalendarNotification(value : Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            calendarNotificationStateFlow.update { value }
        }
    }

    private fun updateLedgerNotification(value : Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            ledgerNotificationStateFlow.update { value }
        }
    }
}

object PreferenceKeys {
    val KEY_CALENDAR = booleanPreferencesKey("calendar_key")
    val KEY_LEDGER = booleanPreferencesKey("ledger_key")
    val KEY_NOTHING = booleanPreferencesKey("nothing")
}

object PendingExtraValue {
    const val KEY = "main_extra"
    const val TARGET_ID_KEY = "extra_target_id_key"
    const val INJECTION_TIME_KEY = "extra_injection_time_key"
    const val INJECTION = "injection"
    const val TODAY_RECORD_MALE = "today_record_male"
    const val TODAY_RECORD_FEMALE= "today_record_female"
}