package com.foregg.presentation.ui.main.calendar.createOrEdit

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.CalendarDatePickerType
import com.foregg.domain.model.enums.CalendarTabType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.RepeatDayType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.request.AddMedicalRecordRequest
import com.foregg.domain.model.request.AddMedicalRecordRequestVo
import com.foregg.domain.model.request.ScheduleDetailRequestVo
import com.foregg.domain.model.request.ScheduleModifyRequestVo
import com.foregg.domain.model.vo.ClassificationVo
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.domain.model.vo.MedicalRecord
import com.foregg.domain.model.vo.RepeatTimeVo
import com.foregg.domain.model.vo.ScheduleDetailVo
import com.foregg.domain.model.vo.ScheduleRepeatDayVo
import com.foregg.domain.model.vo.VolumeVo
import com.foregg.domain.usecase.schedule.GetScheduleDetailUseCase
import com.foregg.domain.usecase.schedule.GetScheduleSideEffectUseCase
import com.foregg.domain.usecase.schedule.PostAddScheduleUseCase
import com.foregg.domain.usecase.schedule.PostUpdateSideEffectUseCase
import com.foregg.domain.usecase.schedule.PutModifyScheduleUseCase
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ForeggLog
import com.foregg.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CreateEditScheduleViewModel @Inject constructor(
    private val postAddScheduleUseCase: PostAddScheduleUseCase,
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase,
    private val getScheduleSideEffectUseCase: GetScheduleSideEffectUseCase,
    private val putModifyScheduleUseCase: PutModifyScheduleUseCase,
    private val postUpdateSideEffectUseCase: PostUpdateSideEffectUseCase
) : BaseViewModel<CreateEditSchedulePageState>() {

    private val viewTypeStateFlow : MutableStateFlow<CalendarType> = MutableStateFlow(CalendarType.CREATE)
    private val tabTypeStateFlow : MutableStateFlow<CalendarTabType> = MutableStateFlow(CalendarTabType.SCHEDULE)
    private val classificationStateFlow : MutableStateFlow<ClassificationVo> = MutableStateFlow(ClassificationVo())
    private val normalDateStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val repeatDayStateFlow : MutableStateFlow<ScheduleRepeatDayVo> = MutableStateFlow(ScheduleRepeatDayVo())
    private val setTimeListStateFlow : MutableStateFlow<List<CreateScheduleTimeVo>> = MutableStateFlow(
        emptyList()
    )
    private val isSpinnerExpandStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val volumeStateFlow : MutableStateFlow<VolumeVo> = MutableStateFlow(VolumeVo())
    private val memoStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val medicalRecordStateFlow : MutableStateFlow<MedicalRecord> = MutableStateFlow(MedicalRecord())
    private val isChangedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: CreateEditSchedulePageState = CreateEditSchedulePageState(
        viewType = viewTypeStateFlow.asStateFlow(),
        tabType = tabTypeStateFlow.asStateFlow(),
        classification = classificationStateFlow.asStateFlow(),
        normalDate = normalDateStateFlow.asStateFlow(),
        repeatDay = repeatDayStateFlow.asStateFlow(),
        setTimeList = setTimeListStateFlow.asStateFlow(),
        isSpinnerExpand = isSpinnerExpandStateFlow.asStateFlow(),
        volume = volumeStateFlow.asStateFlow(),
        memo = memoStateFlow,
        medicalRecord = medicalRecordStateFlow.asStateFlow(),
        isChanged = isChangedStateFlow.asStateFlow()
    )

    private val allDayList = listOf(
        RepeatDayType.MON.week,
        RepeatDayType.TUE.week,
        RepeatDayType.WED.week,
        RepeatDayType.THU.week,
        RepeatDayType.FRI.week,
        RepeatDayType.SAT.week,
        RepeatDayType.SUN.week
    )
    private lateinit var originDetail : ScheduleDetailVo
    private var originMedicalRecord: MedicalRecord = MedicalRecord()
    private var id by Delegates.notNull<Long>()
    private var canCheckChanged : Boolean = false

    fun setViewType(arg : CreateEditScheduleFragmentArgs){
        updateViewType(arg.type)
        when(arg.type){
            CalendarType.CREATE -> updateClassification(arg.scheduleType)
            CalendarType.EDIT -> getScheduleDetail(arg.id)
        }
    }

    fun onClickClassification(){
        emitEventFlow(CreateEditScheduleEvent.ShowSelectScheduleDialog)
    }

    fun onClickInjectionSpinner(){
        viewModelScope.launch {
            isSpinnerExpandStateFlow.update { !isSpinnerExpandStateFlow.value }
        }
    }

    fun onClickDateCalendar(type : CalendarDatePickerType){
        emitEventFlow(CreateEditScheduleEvent.ShowDatePickerDialogEvent(type))
    }

    fun onClickSetRepeatDay(){
        viewModelScope.launch {
            repeatDayStateFlow.update { repeatDayStateFlow.value.copy(isRepeatDay = true) }
            normalDateStateFlow.update { "" }
        }
    }

    fun onClickCancelRepeatDay(){
        viewModelScope.launch {
            repeatDayStateFlow.update { ScheduleRepeatDayVo() }
        }
    }

    fun onClickConfirmRepeatDay(){
        if(repeatDayStateFlow.value.startDate.isEmpty() || repeatDayStateFlow.value.endDate.isEmpty() || repeatDayStateFlow.value.repeatDayList.isEmpty()) return
        updateRepeatDayText(repeatDayStateFlow.value.repeatDayList.joinToString(", "))
    }

    private fun updateRepeatDayText(days : String){
        viewModelScope.launch {
            repeatDayStateFlow.update { repeatDayStateFlow.value.copy(repeatDayText = days) }
        }
    }

    fun setDate(type : CalendarDatePickerType, date : String){
        when(type){
            CalendarDatePickerType.NORMAL -> updateNormalDate(date)
            CalendarDatePickerType.START -> updateStartDate(date)
            CalendarDatePickerType.END -> updateEndDate(date)
        }
    }

    fun onClickRepeatDayBtn(type : RepeatDayType){
        if(type == RepeatDayType.EVERY) {
            checkEveryBtn()
            return
        }
        checkNormalBtn(type)
    }

    private fun checkEveryBtn(){
        val list = if(repeatDayStateFlow.value.repeatDayList.contains(RepeatDayType.EVERY.week)) emptyList()
                else listOf(RepeatDayType.EVERY.week)
        updateRepeatDayList(list)
    }

    private fun checkNormalBtn(type : RepeatDayType){
        if(repeatDayStateFlow.value.repeatDayList.contains(RepeatDayType.EVERY.week)) removeEveryAndThisWeekList(type)
        else if(repeatDayStateFlow.value.repeatDayList.contains(type.week)) removeThisWeekList(type)
        else addThisWeekInList(type)
    }

    private fun removeEveryAndThisWeekList(type : RepeatDayType){
        val list = allDayList.toMutableList()
        list.remove(type.week)
        updateRepeatDayList(list)
    }

    private fun removeThisWeekList(type : RepeatDayType){
        val list = repeatDayStateFlow.value.repeatDayList.filter {
            it != type.week
        }
        updateRepeatDayList(list)
    }

    private fun addThisWeekInList(type : RepeatDayType){
        val list = repeatDayStateFlow.value.repeatDayList.map { it }
        checkIsAllDaySelected(list + listOf(type.week))
    }

    private fun checkIsAllDaySelected(list : List<String>){
        if(list.containsAll(allDayList)) updateRepeatDayList(listOf(RepeatDayType.EVERY.week))
        else updateRepeatDayList(list)
    }

    private fun updateViewType(type : CalendarType){
        viewModelScope.launch {
            viewTypeStateFlow.update { type }
        }
    }

    private fun updateNormalDate(date : String){
        viewModelScope.launch {
            normalDateStateFlow.update { date }
        }
    }

    private fun updateStartDate(date : String){
        viewModelScope.launch {
            repeatDayStateFlow.update { repeatDayStateFlow.value.copy(startDate = date) }
        }
    }

    private fun updateEndDate(date : String){
        viewModelScope.launch {
            repeatDayStateFlow.update { repeatDayStateFlow.value.copy(endDate = date) }
        }
    }

    private fun updateRepeatDayList(list : List<String>){
        viewModelScope.launch {
            repeatDayStateFlow.update { repeatDayStateFlow.value.copy(repeatDayList = list) }
        }
    }

    fun updateClassification(type : RecordType){
        viewModelScope.launch {
            classificationStateFlow.update { ClassificationVo(classification = type) }
            if(type != RecordType.INJECTION) isSpinnerExpandStateFlow.update { false }
        }
    }

    fun updateClassificationDetail(detail : String){
        viewModelScope.launch {
            classificationStateFlow.update { classificationStateFlow.value.copy(classificationDetailEditText = detail) }
            isSpinnerExpandStateFlow.update { false }
        }
    }

    fun updateSetTime(list : List<CreateScheduleTimeVo>){
        viewModelScope.launch {
            setTimeListStateFlow.update { list }
            updateChangedOrigin()
        }
    }

    fun updateTabType(type : CalendarTabType){
        viewModelScope.launch {
            tabTypeStateFlow.update { type }
        }
    }

    fun onClickUploadSchedule(){
        if(checkNotEmpty()){
            when(viewTypeStateFlow.value){
                CalendarType.CREATE -> createSchedule()
                CalendarType.EDIT -> modifySchedule()
            }
        }
        else ForeggLog.D("빈 값 있경")
    }

    private fun checkNotEmpty() : Boolean{
        return classificationStateFlow.value.classificationDetailEditText.isNotEmpty()
                && (normalDateStateFlow.value.isNotEmpty() || (repeatDayStateFlow.value.isNotEmpty() && repeatDayStateFlow.value.isCorrectDay()))
                && setTimeListStateFlow.value.isNotEmpty()
                && volumeStateFlow.value.isNotEmpty(classificationStateFlow.value.classification)
    }

    private fun createSchedule(){
        val request = getDetailRequest()
        viewModelScope.launch {
            postAddScheduleUseCase(request).collect{
                resultResponse(it, { onClickBack() } , {ForeggLog.D("오류")})
            }
        }
    }

    private fun modifySchedule(){
        if(checkMedicalSideEffectChanged()) modifyMedicalSideEffect()
        val request = getModifyDetailRequest()
        viewModelScope.launch {
            putModifyScheduleUseCase(request).collect{
                resultResponse(it, { onClickBack() }, {ForeggLog.D("오류")} )
            }
        }
    }

    private fun modifyMedicalSideEffect(){
        val request = AddMedicalRecordRequestVo(id = id, request = AddMedicalRecordRequest(medicalRecord = medicalRecordStateFlow.value.medicalRecord))
        viewModelScope.launch {
            postUpdateSideEffectUseCase(request).collect{
                resultResponse(it, {}, { ForeggLog.D("에러") })
            }
        }
    }

    private fun getDetailRequest() : ScheduleDetailRequestVo{
        return ScheduleDetailRequestVo(
            recordType = classificationStateFlow.value.classification,
            name = classificationStateFlow.value.classificationDetailEditText,
            date = normalDateStateFlow.value.ifEmpty { null },
            startDate = repeatDayStateFlow.value.startDate.ifEmpty { null },
            endDate = repeatDayStateFlow.value.endDate.ifEmpty { null },
            repeatDate = repeatDayStateFlow.value.repeatDayText.ifEmpty { null },
            repeatTimes = getTimeList(),
            dose = getDose(),
            memo = memoStateFlow.value,
        )
    }

    private fun getModifyDetailRequest() : ScheduleModifyRequestVo {
        return ScheduleModifyRequestVo(id = id, request = getDetailRequest())
    }

    private fun getTimeList() : List<RepeatTimeVo> {
        return setTimeListStateFlow.value.map {
            RepeatTimeVo(TimeFormatter.formatObjectTimeToStringTime(it))
        }
    }

    private fun getDose() : String? {
        return when(classificationStateFlow.value.classification){
            RecordType.MEDICINE -> "${volumeStateFlow.value.medicineVolumeDay}/${volumeStateFlow.value.medicineVolumeCount}/${volumeStateFlow.value.medicineVolumeRound}"
            RecordType.INJECTION -> volumeStateFlow.value.injectionVolume

            RecordType.HOSPITAL,
            RecordType.ETC -> null
        }
    }

    private fun updateMedicineVolume(dose : String){
        viewModelScope.launch {
            volumeStateFlow.update { it.copy(
                medicineVolumeDay = dose.split("/")[0],
                medicineVolumeCount = dose.split("/")[1],
                medicineVolumeRound = dose.split("/")[2]
            ) }
        }
    }

    private fun updateInjectionVolume(dose : String){
        viewModelScope.launch {
            volumeStateFlow.update { it.copy(injectionVolume = dose) }
        }
    }

    private fun updateMemo(memo : String){
        viewModelScope.launch {
            memoStateFlow.update { memo }
        }
    }

    fun onClickBack(){
        emitEventFlow(CreateEditScheduleEvent.GoToBackEvent)
    }

    private fun getScheduleDetail(id : Long){
        this.id = id
        viewModelScope.launch {
            getScheduleDetailUseCase(id).collect {
                resultResponse(it, ::handleGetDetailSuccess)
            }
        }
    }

    private fun handleGetDetailSuccess(result : ScheduleDetailVo){
        originDetail = result
        updateClassification(result.recordType)
        updateClassificationDetail(result.name)
        updateDate(result)
        updateSetTime(result.repeatTimes.map { TimeFormatter.formatStringTimeToObjectTime(it.time) })
        when(result.recordType){
            RecordType.MEDICINE -> result.dose?.let { updateMedicineVolume(it) }
            RecordType.INJECTION -> result.dose?.let { updateInjectionVolume(it) }
            RecordType.HOSPITAL -> getMedicalRecord()
            RecordType.ETC -> {}
        }
        updateMemo(result.memo)
        canCheckChanged = true
    }

    private fun updateDate(result : ScheduleDetailVo){
        if(result.date != null) updateNormalDate(result.date!!)
        else{
            onClickSetRepeatDay()
            updateStartDate(result.startDate!!)
            updateEndDate(result.endDate!!)
            result.repeatDate?.let { updateRepeatDayText(it) }
        }
    }

    private fun getMedicalRecord(){
        viewModelScope.launch {
            getScheduleSideEffectUseCase(id).collect{
                resultResponse(it, ::handleGetSideEffectSuccess, { ForeggLog.D("ERROR")})
            }
        }
    }

    private fun handleGetSideEffectSuccess(result : MedicalRecord){
        originMedicalRecord = result
        viewModelScope.launch {
            medicalRecordStateFlow.update { it.copy(medicalRecord = result.medicalRecord, medicalSideEffect = result.medicalSideEffect) }
        }
    }

    fun updateChangedOrigin(){
        val isChanged = if(canCheckChanged) checkChangedContent() else false
        viewModelScope.launch {
            isChangedStateFlow.update { isChanged }
        }
    }

    private fun checkChangedContent() : Boolean{
        return originDetail.name != classificationStateFlow.value.classificationDetailEditText
                || checkDateChanged()
                || checkTimeChanged()
                || checkDoseChanged()
                || originDetail.memo != memoStateFlow.value
                || checkMedicalSideEffectChanged()
    }

    private fun checkDateChanged() : Boolean {
        return if(originDetail.date != null) originDetail.date != normalDateStateFlow.value
                else originDetail.repeatDate != repeatDayStateFlow.value.repeatDayText
                    || originDetail.startDate != repeatDayStateFlow.value.startDate
                    || originDetail.endDate != repeatDayStateFlow.value.endDate
    }


    private fun checkTimeChanged() : Boolean {
        val origin = originDetail.repeatTimes.map { it.time }.sorted()
        val timeList = setTimeListStateFlow.value.map { TimeFormatter.formatObjectTimeToStringTime(it) }.sorted()
        return origin != timeList
    }

    private fun checkDoseChanged() : Boolean {
        return when(originDetail.recordType){
            RecordType.MEDICINE -> {
                (originDetail.dose?.split("/")?.get(0) ?: "") != volumeStateFlow.value.medicineVolumeDay
                        || (originDetail.dose?.split("/")?.get(1) ?: "") != volumeStateFlow.value.medicineVolumeCount
                        || (originDetail.dose?.split("/")?.get(2) ?: "") != volumeStateFlow.value.medicineVolumeRound
            }
            RecordType.INJECTION -> {
                originDetail.dose != volumeStateFlow.value.injectionVolume
            }
            RecordType.HOSPITAL,
            RecordType.ETC -> false
        }
    }

    private fun checkMedicalSideEffectChanged() : Boolean{
        ForeggLog.D(originMedicalRecord.medicalRecord)
        ForeggLog.D(medicalRecordStateFlow.value.medicalRecord)
        ForeggLog.D((originMedicalRecord.medicalRecord != medicalRecordStateFlow.value.medicalRecord).toString())
        return if(originDetail.recordType == RecordType.HOSPITAL){
            originMedicalRecord.medicalRecord != medicalRecordStateFlow.value.medicalRecord
        }
        else false
    }
}