package com.foregg.presentation.ui

import androidx.lifecycle.viewModelScope
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<MainActivityPageState>() {

    private val isCalendarPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isAccountPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isMainPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isInfoPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isProfilePageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isOtherPageStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: MainActivityPageState = MainActivityPageState(
        isCalendarPageStateFlow.asStateFlow(),
        isAccountPageStateFlow.asStateFlow(),
        isMainPageStateFlow.asStateFlow(),
        isInfoPageStateFlow.asStateFlow(),
        isProfilePageStateFlow.asStateFlow(),
        isOtherPageStateFlow.asStateFlow()
    )

    fun onClickCalendar() {
        if(isCalendarPageStateFlow.value) return
        activeCalendar()
        emitEventFlow(MainActivityEvent.GoToCalendar)
    }

    fun onClickAccount() {
        if(isAccountPageStateFlow.value) return
        activeAccount()
        emitEventFlow(MainActivityEvent.GoToAccount)
    }

    fun onClickMain() {
        if(isMainPageStateFlow.value) return
        activeMain()
        emitEventFlow(MainActivityEvent.GoToMain)
    }

    fun onClickInfo() {
        if(isInfoPageStateFlow.value) return
        activeInfo()
        emitEventFlow(MainActivityEvent.GoToInfo)
    }

    fun onClickProfile() {
        if(isProfilePageStateFlow.value) return
        activeProfile()
        emitEventFlow(MainActivityEvent.GoToProfile)
    }

    fun activeCalendar(){
        viewModelScope.launch {
            isMainPageStateFlow.update { false }
            isCalendarPageStateFlow.update { true }
            isProfilePageStateFlow.update { false }
            isInfoPageStateFlow.update { false }
            isAccountPageStateFlow.update { false }
            isOtherPageStateFlow.update { false }
        }
    }

    fun activeAccount(){
        viewModelScope.launch {
            isMainPageStateFlow.update { false }
            isCalendarPageStateFlow.update { false }
            isProfilePageStateFlow.update { false }
            isInfoPageStateFlow.update { false }
            isAccountPageStateFlow.update { true }
            isOtherPageStateFlow.update { false }
        }
    }

    fun activeMain(){
        viewModelScope.launch {
            isMainPageStateFlow.update { true }
            isCalendarPageStateFlow.update { false }
            isProfilePageStateFlow.update { false }
            isInfoPageStateFlow.update { false }
            isAccountPageStateFlow.update { false }
            isOtherPageStateFlow.update { false }
        }
    }

    fun activeProfile(){
        viewModelScope.launch {
            isMainPageStateFlow.update { false }
            isCalendarPageStateFlow.update { false }
            isProfilePageStateFlow.update { true }
            isInfoPageStateFlow.update { false }
            isAccountPageStateFlow.update { false }
            isOtherPageStateFlow.update { false }
        }
    }

    fun activeInfo(){
        viewModelScope.launch {
            isMainPageStateFlow.update { false }
            isCalendarPageStateFlow.update { false }
            isProfilePageStateFlow.update { false }
            isInfoPageStateFlow.update { true }
            isAccountPageStateFlow.update { false }
            isOtherPageStateFlow.update { false }
        }
    }

    fun goneNavigation(){
        viewModelScope.launch {
            isOtherPageStateFlow.update { true }
        }
    }
}