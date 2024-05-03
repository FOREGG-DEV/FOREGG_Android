package com.foregg.presentation.ui.main.calendar.createOrEdit

import com.foregg.domain.model.enums.CalendarTabType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.vo.ClassificationVo
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.domain.model.vo.MedicalRecord
import com.foregg.domain.model.vo.ScheduleRepeatDayVo
import com.foregg.domain.model.vo.VolumeVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateEditSchedulePageState(
    val viewType : StateFlow<CalendarType>,
    val tabType : StateFlow<CalendarTabType>,
    val classification : StateFlow<ClassificationVo>,
    val normalDate : StateFlow<String>,
    val repeatDay: StateFlow<ScheduleRepeatDayVo>,
    val setTimeList : StateFlow<List<CreateScheduleTimeVo>>,
    val isSpinnerExpand : StateFlow<Boolean>,
    val volume: StateFlow<VolumeVo>,
    var memo : MutableStateFlow<String>,
    val medicalRecord : StateFlow<MedicalRecord>,
    val isChanged : StateFlow<Boolean>
) : PageState