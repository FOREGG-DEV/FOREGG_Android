package com.foregg.presentation.ui.main.information

import com.foregg.domain.model.vo.InfoItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : BaseViewModel<InformationPageState>() {
    private val infoListStateFlow: MutableStateFlow<Map<String, List<InfoItemVo>>> = MutableStateFlow(
        mapOf(
            resourceProvider.getString(R.string.info_pregnancy) to listOf(InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"), InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"), InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법")),
            resourceProvider.getString(R.string.info_infertility) to listOf(InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"), InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"), InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"))
        )
    )

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )

    fun onClickBtnDetail(position: Int) {
        when(position) {
            0 -> emitEventFlow(InformationEvent.GoToPregnancyDetailEvent)
            1 -> emitEventFlow(InformationEvent.GoToInfertilityDetailEvent)
        }

    }

    fun onClickBtnBack() {
        emitEventFlow(InformationEvent.GoBackEvent)
    }
}