package com.foregg.presentation.ui.main.account

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.AccountTabType
import com.foregg.domain.model.vo.AccountCardVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.ui.main.calendar.CalendarViewModel
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModel<AccountPageState>() {

    private val tabTypeStateFlow : MutableStateFlow<AccountTabType> = MutableStateFlow(AccountTabType.ALL)
    private val selectTextStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val startDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val endDayStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val startAndEndStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val allExpendStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val subsidyStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val personalStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val accountListStateFlow : MutableStateFlow<List<AccountCardVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: AccountPageState = AccountPageState(
        tabType = tabTypeStateFlow.asStateFlow(),
        selectText = selectTextStateFlow.asStateFlow(),
        startDay = startDayStateFlow.asStateFlow(),
        endDay = endDayStateFlow.asStateFlow(),
        startAndEnd = startAndEndStateFlow.asStateFlow(),
        allExpend = allExpendStateFlow.asStateFlow(),
        subsidy = subsidyStateFlow.asStateFlow(),
        personal = personalStateFlow.asStateFlow(),
        accountList = accountListStateFlow.asStateFlow()
    )

    companion object{
        const val JANUARY = 1
        const val DECEMBER = 12
    }

    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()
    private var round by Delegates.notNull<Int>()

    fun setView(){
        val today = TimeFormatter.getToday()
        year = TimeFormatter.getYear(today).toInt()
        month = TimeFormatter.getMonth(today).toInt()
        round = 1
        initDay(today)
    }

    private fun initDay(date : String){
        val prevMonthDay = TimeFormatter.getPreviousMonthDate()
        viewModelScope.launch {
            startDayStateFlow.update { date }
            endDayStateFlow.update { prevMonthDay }
            updateStartAndEndStateFlow("${TimeFormatter.getDotsDate(prevMonthDay)} - ${TimeFormatter.getDotsDate(date)}")
        }
    }

    private fun updateStartAndEndStateFlow(dateText : String){
        viewModelScope.launch {
            startAndEndStateFlow.update { dateText }
        }
    }

    fun updateTabType(type : AccountTabType){
        viewModelScope.launch {
            tabTypeStateFlow.update { type }
            inspectSelectText(type)
        }
    }

    private fun inspectSelectText(type : AccountTabType){
        when(type){
            AccountTabType.ALL -> updateSelectText("")
            AccountTabType.ROUND -> updateSelectText(resourceProvider.getString(R.string.account_round_unit, round))
            AccountTabType.MONTH ->  updateSelectText(resourceProvider.getString(R.string.calendar_year_and_month, year, month))
        }
    }

    private fun updateSelectText(text : String){
        viewModelScope.launch {
            selectTextStateFlow.update { text }
        }
    }

    fun onClickNext(){
        when(tabTypeStateFlow.value){
            AccountTabType.ALL -> {}
            AccountTabType.ROUND -> nextRound()
            AccountTabType.MONTH -> nextMonth()
        }
        inspectSelectText(tabTypeStateFlow.value)
    }

    fun onClickPrev(){
        when(tabTypeStateFlow.value){
            AccountTabType.ALL -> {}
            AccountTabType.ROUND -> prevRound()
            AccountTabType.MONTH -> prevMonth()
        }
        inspectSelectText(tabTypeStateFlow.value)
    }

    private fun nextMonth(){
        if(month == DECEMBER){
            year++
            month = JANUARY
        }
        else{
            month++
        }
    }

    private fun nextRound(){
        round++
    }

    private fun prevMonth(){
        if(month == JANUARY){
            year--
            month = DECEMBER
        }
        else{
            month--
        }
    }

    private fun prevRound(){
        if(round == 1) return
        round--
    }
}