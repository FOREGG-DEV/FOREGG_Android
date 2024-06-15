package com.foregg.presentation.ui.main.information.subsidyDetail

import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SubsidyDetailViewModel @Inject constructor() : BaseViewModel<SubsidyDetailPageState>() {
    private val subsidyListStateFlow: MutableStateFlow<List<InfoItemVo>> = MutableStateFlow(listOf(
        InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"),
        InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"),
        InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"),
        InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법"),
        InfoItemVo("#태아위협 #미세플라스틱\n#줄이는법")
    ))

    override val uiState: SubsidyDetailPageState = SubsidyDetailPageState(
        subsidyList = subsidyListStateFlow.asStateFlow()
    )

    fun onClickBack(){
        emitEventFlow(SubsidyDetailEvent.OnClickBack)
    }
}