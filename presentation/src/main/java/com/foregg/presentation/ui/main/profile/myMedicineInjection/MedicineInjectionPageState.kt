package com.foregg.presentation.ui.main.profile.myMedicineInjection

import com.foregg.domain.model.enums.ProfileMedicineInjectionType
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MedicineInjectionPageState(
    val tabType : StateFlow<ProfileMedicineInjectionType>,
    val itemList : StateFlow<List<MyMedicineInjectionResponseVo>>
) : PageState