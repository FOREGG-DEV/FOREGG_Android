package com.foregg.presentation.ui

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.BottomNavType
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<MainActivityPageState>() {

    private val pageTypeStateFlow : MutableStateFlow<BottomNavType> = MutableStateFlow(BottomNavType.FIRST)

    override val uiState: MainActivityPageState = MainActivityPageState(
        pageType = pageTypeStateFlow.asStateFlow()
    )

    fun onClickCalendar() {
        emitEventFlow(MainActivityEvent.GoToCalendar)
    }

    fun onClickAccount() {
        emitEventFlow(MainActivityEvent.GoToAccount)
    }

    fun onClickHome() {
        emitEventFlow(MainActivityEvent.GoToMain)
    }

    fun onClickInfo() {
        emitEventFlow(MainActivityEvent.GoToInfo)
    }

    fun onClickProfile() {
        emitEventFlow(MainActivityEvent.GoToProfile)
    }

    fun updatePageType(type : BottomNavType){
        viewModelScope.launch {
            pageTypeStateFlow.update { type }
        }
    }
}