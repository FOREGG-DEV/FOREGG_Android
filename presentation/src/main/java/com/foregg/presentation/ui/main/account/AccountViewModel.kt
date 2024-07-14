package com.foregg.presentation.ui.main.account

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.enums.AccountTabType
import com.foregg.domain.model.request.account.AccountGetConditionRequestVo
import com.foregg.domain.model.response.account.AccountResponseVo
import com.foregg.domain.model.vo.account.AccountCardVo
import com.foregg.domain.usecase.account.DeleteAccountUseCase
import com.foregg.domain.usecase.account.GetByConditionAccountUseCase
import com.foregg.domain.usecase.account.GetByCountAccountUseCase
import com.foregg.domain.usecase.account.GetByMonthAccountUseCase
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getByConditionAccountUseCase: GetByConditionAccountUseCase,
    private val getByCountAccountUseCase: GetByCountAccountUseCase,
    private val getByMonthAccountUseCase: GetByMonthAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
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

    private val today = TimeFormatter.getToday()
    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()
    private var round by Delegates.notNull<Int>()

    init {
        year = TimeFormatter.getYear(today).toInt()
        month = TimeFormatter.getMonth(today).toInt()
        round = 1
    }

    fun setView(){
        if(startAndEndStateFlow.value.isEmpty()) initDay(TimeFormatter.getPreviousMonthDate(), today)
        when(tabTypeStateFlow.value){
            AccountTabType.ALL -> getAccountByCondition()
            AccountTabType.ROUND -> getAccountByRound()
            AccountTabType.MONTH -> getAccountByMonth()
        }
    }

    fun initDay(start : String, end : String){
        viewModelScope.launch {
            startDayStateFlow.update { start }
            endDayStateFlow.update { end }
            updateStartAndEndStateFlow("${TimeFormatter.getDotsDate(start)} - ${TimeFormatter.getDotsDate(end)}")
        }
    }

    fun getAccountByCondition(){
        val request = AccountGetConditionRequestVo(
            from = startDayStateFlow.value,
            to = endDayStateFlow.value
        )
        viewModelScope.launch {
            getByConditionAccountUseCase(request).collect{
                resultResponse(it, ::handleGetSuccessAccount)
            }
        }
    }

    private fun getAccountByMonth(){
        val request = getByMonthRequest()
        viewModelScope.launch {
            getByMonthAccountUseCase(request).collect{
                resultResponse(it, ::handleGetSuccessAccount)
            }
        }
    }

    private fun getByMonthRequest() : String{
        val requestMonth = String.format("%02d", month)
        return "$year-$requestMonth"
    }

    private fun getAccountByRound(){
        viewModelScope.launch {
            getByCountAccountUseCase(round).collect{
                resultResponse(it, ::handleGetSuccessAccount)
            }
        }
    }


    private fun handleGetSuccessAccount(result : AccountResponseVo){
        updateAllExpend(getMoneyFormat(result.allExpendMoney))
        updateSubsidy(getMoneyFormat(result.subsidyMoney))
        updatePersonal(getMoneyFormat(result.personalMoney))
        updateAccountCard(result.accountList)
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
            AccountTabType.ALL -> {
                getAccountByCondition()
                updateSelectText("")
            }
            AccountTabType.ROUND -> {
                getAccountByRound()
                updateSelectText(resourceProvider.getString(R.string.account_round_unit, round))
            }
            AccountTabType.MONTH ->  {
                getAccountByMonth()
                setFilterDayByMonth()
                updateSelectText(resourceProvider.getString(R.string.calendar_year_and_month, year, month))
            }
        }
    }

    private fun setFilterDayByMonth(){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val yearMonth = YearMonth.of(year, month)
        val startDate = yearMonth.atDay(1).format(formatter)
        val endDate = yearMonth.atEndOfMonth().format(formatter)
        initDay(startDate, endDate)
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

    private fun updateAllExpend(money : String){
        viewModelScope.launch {
            allExpendStateFlow.update { money }
        }
    }

    private fun updateSubsidy(money : String){
        viewModelScope.launch {
            subsidyStateFlow.update { money }
        }
    }

    private fun updatePersonal(money : String){
        viewModelScope.launch {
            personalStateFlow.update { money }
        }
    }

    private fun updateAccountCard(list : List<AccountCardVo>){
        viewModelScope.launch {
            accountListStateFlow.update { list }
        }
    }

    private fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return resourceProvider.getString(R.string.account_money_unit, koreanFormat.format(money))
    }

    fun updateSelectedCard(item : AccountCardVo){
        val newList = accountListStateFlow.value.map {
            if(it.id == item.id) it.copy(isSelected = item.isSelected) else it
        }
        updateAccountCard(newList)
    }

    fun onClickCreateOrDeleteBtn(){
        emitEventFlow(AccountEvent.OnClickAddOrDeleteBtn)
    }

    fun onClickDeleteBtn(){
        val request = accountListStateFlow.value.find { it.isSelected }?.id ?: -1
        viewModelScope.launch {
            deleteAccountUseCase(request).collect{
                resultResponse(it, {inspectSelectText(tabTypeStateFlow.value)}, ::handleDeleteError)
            }
        }
    }

    fun onClickChangeDate(){
        emitEventFlow(AccountEvent.ShowBottomSheetEvent(startDayStateFlow.value, endDayStateFlow.value))
    }

    private fun handleDeleteError(error : String){
        when(error){
            StatusCode.LEDGER.NO_EXIST_LEDGER -> emitEventFlow(AccountEvent.ErrorNotExist)
        }
    }
}