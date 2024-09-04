package com.hugg.presentation.ui.main.profile.myMedicineInjection

import com.hugg.presentation.Event

sealed class MedicineInjectionEvent : Event {
    object GoToBackEvent : MedicineInjectionEvent()
}