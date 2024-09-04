package com.hugg.presentation.ui.main.profile.myMedicineInjection

import com.hugg.domain.model.enums.ProfileMedicineInjectionType
import com.hugg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class MedicineInjectionPageState(
    val tabType : StateFlow<ProfileMedicineInjectionType>,
    val itemList : StateFlow<List<MyMedicineInjectionResponseVo>>
) : PageState