package com.hugg.presentation.ui.main.calendar.createOrEdit

import com.hugg.domain.model.enums.CalendarTabType
import com.hugg.domain.model.enums.CalendarType
import com.hugg.domain.model.vo.ClassificationVo
import com.hugg.domain.model.vo.CreateScheduleTimeVo
import com.hugg.domain.model.vo.MedicalRecord
import com.hugg.domain.model.vo.ScheduleRepeatDayVo
import com.hugg.domain.model.vo.VolumeVo
import com.hugg.presentation.PageState
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