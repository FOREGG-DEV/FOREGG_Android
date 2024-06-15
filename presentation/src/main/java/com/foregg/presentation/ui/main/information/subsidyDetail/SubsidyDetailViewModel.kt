package com.foregg.presentation.ui.main.information.subsidyDetail

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.InformationType
import com.foregg.domain.model.response.information.InformationResponseVo
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.domain.usecase.information.GetAllInformationByTypeUseCase
import com.foregg.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubsidyDetailViewModel @Inject constructor(
    private val getAllInformationByTypeUseCase: GetAllInformationByTypeUseCase
) : BaseViewModel<SubsidyDetailPageState>() {

    private val subsidyListStateFlow: MutableStateFlow<List<InfoItemVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: SubsidyDetailPageState = SubsidyDetailPageState(
        subsidyList = subsidyListStateFlow.asStateFlow()
    )

    fun getDetailList(type : InformationType){
        when(type){
            InformationType.ESSENTIAL -> getEssentialList()
            InformationType.HUGG_PICK -> getHuggPickList()
            InformationType.NOTHING -> {}
        }
    }

    private fun getInformationListByType(type: InformationType){
        viewModelScope.launch {
            getAllInformationByTypeUseCase(type).collect{
                resultResponse(it, {})
            }
        }
    }

    private fun handleSuccessGetInformationList(result : List<InformationResponseVo>){
        viewModelScope.launch {
            subsidyListStateFlow.update { getItemList(result) }
        }
    }

    private fun getItemList(list : List<InformationResponseVo>) : List<InfoItemVo>{
        return list.map {
            InfoItemVo(
                url = it.url,
                tags = it.tag,
                image = it.image
            )
        }
    }

    private fun getEssentialList(){
        viewModelScope.launch {
            subsidyListStateFlow.update { getEssentialDummy() }
        }
    }

    private fun getHuggPickList(){
        viewModelScope.launch {
            subsidyListStateFlow.update { getHuggPickDummy() }
        }
    }

    fun onClickBack(){
        emitEventFlow(SubsidyDetailEvent.OnClickBack)
    }

    private fun getEssentialDummy() : List<InfoItemVo>{
        return listOf(
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223471809266",
                tags = listOf("#한큐주스","#건강주스","#난소질개선"),
                image = "https://drive.google.com/uc?export=download&id=1ptFXoNGNd6Y-jzNb1JplZcvJMhc63s4q"
            ),
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223461185285",
                tags = listOf("#난임공감","#난임전자책","#난임일기"),
                image = "https://drive.google.com/uc?export=download&id=1Zpsxcb_Frw83cG_vXqDd0_RQXbUDWvLF"
            ),
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223456196365",
                tags = listOf("#난임공감","#난임소설책","#난임일기"),
                image = "https://drive.google.com/uc?export=download&id=1ZRbbbdMxgVnTw1JqOtGdgalw53KEaMnX"
            )
        )
    }

    private fun getHuggPickDummy() : List<InfoItemVo>{
        return listOf(
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223473055862",
                tags = listOf("#난임과 음주","#시험관 음주","#남편 술"),
                image = "https://drive.google.com/uc?export=download&id=1-P1Ar5YxxJZknazDoPVUA0OMImOrSZAX"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223448519504",
                tags = listOf("#난임에 좋은 음식","#식단관리","#항산화음식"),
                image = "https://drive.google.com/uc?export=download&id=1HSz85v1Ld78-KJV8v9m_nQ6Xq3TIX2Le"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223449818880",
                tags = listOf("#난임영앙제추천","#코엔자임","#코큐텐"),
                image = "https://drive.google.com/uc?export=download&id=1s-5e0PkwHegOQfCAGcsDz0Y-mnxA6nTR"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223451740757",
                tags = listOf("#난임스트레스","#스트레스관리법"),
                image = "https://drive.google.com/uc?export=download&id=1eUKl0TQD3r-13U0418d3yV-ivmIiZpcA"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223465584034",
                tags = listOf("#태아위협","#미세플라스틱","#줄이는 방법"),
                image = "https://drive.google.com/uc?export=download&id=1heBmhRSK0gXBN7ZfYA9QqOMcsB3ZQ4v3"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223452355860",
                tags = listOf("#난임영양제","#비타민D"),
                image = "https://drive.google.com/uc?export=download&id=1h3hwq4uhgDOdwwhkoA1MKQSbzYGMn428"
            )
        )
    }
}