package com.foregg.presentation.ui.main.account.createOrEdit

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.AccountType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AccountCreateEditViewModel @Inject constructor() : BaseViewModel<AccountCreateEditPageState>() {

    private val viewTypeStateFlow : MutableStateFlow<CalendarType> = MutableStateFlow(CalendarType.CREATE)
    private val tabTypeStateFlow : MutableStateFlow<AccountType> = MutableStateFlow(AccountType.PERSONAL_EXPENSE)
    private val selectDateStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val moneyStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val roundStateFlow : MutableStateFlow<Int> = MutableStateFlow(0)
    private val memoStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isChangedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: AccountCreateEditPageState = AccountCreateEditPageState(
        viewType = viewTypeStateFlow.asStateFlow(),
        tabType = tabTypeStateFlow.asStateFlow(),
        selectDate = selectDateStateFlow.asStateFlow(),
        content = contentStateFlow,
        money = moneyStateFlow,
        round = roundStateFlow.asStateFlow(),
        memo = memoStateFlow,
        isChanged = isChangedStateFlow.asStateFlow()
    )

    companion object{
        const val MIN_ROUND = -1
        const val MAX_ROUND = 100
    }

    private lateinit var originDetail : ScheduleDetailVo
    private var id by Delegates.notNull<Long>()

    fun setViewType(args : AccountCreateEditFragmentArgs){
        when(args.type){
            CalendarType.CREATE -> {}
            CalendarType.EDIT -> {}
        }
    }

    fun setTabType(type: AccountType){
        viewModelScope.launch {
            tabTypeStateFlow.update { type }
        }
    }

    fun setDate(date : String){
        viewModelScope.launch {
            selectDateStateFlow.update { date }
        }
    }

    fun onTextChangedMoney(){
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        val stringWithoutComma = moneyStateFlow.value.replace(",", "")
        val formattedNumber = koreanFormat.format(stringWithoutComma.toInt())
        viewModelScope.launch {
            moneyStateFlow.update { formattedNumber }
        }
    }

    fun onClickBack(){
        emitEventFlow(AccountCreateEditEvent.GoToBackEvent)
    }

    fun onClickDatePicker(){
        emitEventFlow(AccountCreateEditEvent.ShowDatePickerDialog)
    }

    fun onClickMinus(){
        val round = roundStateFlow.value - 1
        if(round != MIN_ROUND){
            updateProgressRound(round)
        }
    }

    fun onClickPlus(){
        val round = roundStateFlow.value + 1
        if(round != MAX_ROUND){
            updateProgressRound(round)
        }
    }

    private fun updateProgressRound(round : Int){
        viewModelScope.launch {
            roundStateFlow.update { round }
        }
    }
}