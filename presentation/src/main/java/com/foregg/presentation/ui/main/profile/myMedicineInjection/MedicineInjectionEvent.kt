package com.foregg.presentation.ui.main.profile.myMedicineInjection

import com.foregg.presentation.Event

sealed class MedicineInjectionEvent : Event {
    object GoToBackEvent : MedicineInjectionEvent()
}