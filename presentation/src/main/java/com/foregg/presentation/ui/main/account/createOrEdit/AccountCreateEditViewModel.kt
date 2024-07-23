package com.foregg.presentation.ui.main.account.createOrEdit

import androidx.lifecycle.viewModelScope
import com.foregg.data.base.StatusCode
import com.foregg.domain.model.enums.AccountType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.request.account.AccountCreateRequestVo
import com.foregg.domain.model.request.account.AccountEditRequestVo
import com.foregg.domain.model.response.account.AccountDetailResponseVo
import com.foregg.domain.usecase.account.GetAccountDetailUseCase
import com.foregg.domain.usecase.account.PostCreateAccountUseCase
import com.foregg.domain.usecase.account.PutEditAccountUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggAnalytics
import com.foregg.presentation.util.UserInfo
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
class AccountCreateEditViewModel @Inject constructor(
    private val getAccountDetailUseCase: GetAccountDetailUseCase,
    private val putEditAccountUseCase: PutEditAccountUseCase,
    private val postCreateAccountUseCase: PostCreateAccountUseCase
) : BaseViewModel<AccountCreateEditPageState>() {

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

    private lateinit var originDetail : AccountDetailResponseVo
    private var id by Delegates.notNull<Long>()
    private var canCheckChanged : Boolean = false

    fun setViewType(args : AccountCreateEditFragmentArgs){
        viewModelScope.launch {
            viewTypeStateFlow.update { args.type }
        }
        if(args.type == CalendarType.EDIT) getAccountDetail(args.id)
    }

    private fun getAccountDetail(id : Long){
        this.id = id
        viewModelScope.launch{
            getAccountDetailUseCase(id).collect{
                resultResponse(it, ::handleSuccessGetAccountDetail, ::handleGetAccountDetailError)
            }
        }
    }

    private fun handleSuccessGetAccountDetail(result : AccountDetailResponseVo){
        originDetail = result
        setTabType(result.type)
        setDate(result.date)
        updateContent(result.content)
        updateMoney(result.money)
        updateProgressRound(result.round)
        updateMemo(result.memo)
        canCheckChanged = true
    }

    private fun handleGetAccountDetailError(error : String){
        when(error){
            StatusCode.LEDGER.NO_EXIST_LEDGER -> emitEventFlow(AccountCreateEditEvent.ErrorNotExist)
        }
    }

    fun setTabType(type: AccountType){
        viewModelScope.launch {
            tabTypeStateFlow.update { type }
            updateChangedOrigin()
        }
    }

    fun setDate(date : String){
        viewModelScope.launch {
            selectDateStateFlow.update { date }
        }
    }

    private fun updateContent(content : String){
        viewModelScope.launch {
            contentStateFlow.update { content }
        }
    }

    private fun updateMemo(memo : String){
        viewModelScope.launch {
            memoStateFlow.update { memo }
        }
    }

    fun onTextChangedMoney(){
        if(moneyStateFlow.value.isEmpty()) return
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        val stringWithoutComma = moneyStateFlow.value.replace(",", "")
        val formattedNumber = koreanFormat.format(stringWithoutComma.toInt())
        updateMoney(formattedNumber)
    }

    private fun updateMoney(money : String){
        viewModelScope.launch {
            moneyStateFlow.update { money }
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

    fun onClickUploadBtn(){
        if(isEmpty()){
            emitEventFlow(AccountCreateEditEvent.ErrorBlankContent)
            return
        }
        when(viewTypeStateFlow.value){
            CalendarType.CREATE -> createAccount()
            CalendarType.EDIT -> editAccount()
        }
    }

    private fun createAccount(){
        val request = getCreateRequest()
        viewModelScope.launch {
            postCreateAccountUseCase(request).collect{
                resultResponse(it, { handleSuccessCreateAccount() })
            }
        }
    }

    private fun handleSuccessCreateAccount(){
        onClickBack()
        ForeggAnalytics.logEvent("account_${UserInfo.info.genderType.type}_add", "AccountCreateEditFragment")
    }

    private fun editAccount(){
        val request = getEditRequest()
        viewModelScope.launch {
            putEditAccountUseCase(request).collect{
                resultResponse(it, { onClickBack() }, ::handleGetAccountDetailError)
            }
        }
    }

    private fun getEditRequest() : AccountEditRequestVo{
        return AccountEditRequestVo(
            id = id,
            request = getCreateRequest()
        )
    }

    private fun getCreateRequest() : AccountCreateRequestVo{
        return AccountCreateRequestVo(
            ledgerType = tabTypeStateFlow.value,
            date = selectDateStateFlow.value,
            content = contentStateFlow.value,
            amount = moneyStateFlow.value.replace(",", "").toInt(),
            count = roundStateFlow.value,
            memo = memoStateFlow.value
        )
    }

    fun updateChangedOrigin(){
        val isChanged = if(canCheckChanged) checkChangedContent() else false
        viewModelScope.launch {
            isChangedStateFlow.update { isChanged }
        }
    }

    private fun checkChangedContent() : Boolean{
        return originDetail.type != tabTypeStateFlow.value
                || originDetail.date != selectDateStateFlow.value
                || originDetail.content != contentStateFlow.value
                || originDetail.money != moneyStateFlow.value.replace(",", "")
                || originDetail.round != roundStateFlow.value
                || originDetail.memo != memoStateFlow.value
    }

    private fun updateProgressRound(round : Int){
        viewModelScope.launch {
            roundStateFlow.update { round }
        }
    }

    private fun isEmpty() : Boolean {
        return selectDateStateFlow.value.isEmpty() || contentStateFlow.value.isEmpty() || moneyStateFlow.value.isEmpty()
    }
}