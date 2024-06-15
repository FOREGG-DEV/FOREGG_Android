package com.foregg.presentation.ui.main.information

import androidx.lifecycle.viewModelScope
import com.foregg.domain.model.enums.InfoCategoryType
import com.foregg.domain.model.vo.info.InfoCategoryListVo
import com.foregg.domain.model.vo.info.InfoItemVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseViewModel
import com.foregg.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor() : BaseViewModel<InformationPageState>() {

    private val infoListStateFlow : MutableStateFlow<List<InfoCategoryListVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: InformationPageState = InformationPageState(
        infoList = infoListStateFlow.asStateFlow()
    )

    fun getInformation(){
        handleSuccessGetInformation()
    }

    private fun handleSuccessGetInformation(){
        viewModelScope.launch {
            infoListStateFlow.update { getDummy() }
        }
    }

    private fun getDummy() : List<InfoCategoryListVo>{
        return listOf(
            InfoCategoryListVo(
                type = InfoCategoryType.ESSENTIAL,
                list = getEssentialDummy()
            ),
            InfoCategoryListVo(
                type = InfoCategoryType.HUGG_PICK,
                list = getHuggPickDummy()
            )
        )
    }

    private fun getEssentialDummy() : List<InfoItemVo>{
        return listOf(
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223471809266",
                tags = listOf("#한큐주스","#건강주스","#난소질개선"),
                image = "https://drive.google.com/uc?export=download&id=1nds76_H4cpjoBEZKF3ScLC3KNToR-wFk"
            ),
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223461185285",
                tags = listOf("#난임공감","#난임전자책","#난임일기"),
                image = "https://drive.google.com/uc?export=download&id=1vjOEka4oS4CYovfgKetV84ksEOqk230p"
            ),
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223456196365",
                tags = listOf("#난임공감","#난임소설책","#난임일기"),
                image = "https://drive.google.com/uc?export=download&id=1tIM6cRmFckl0cqB_5jvsvnu9qWOTVncK"
            )
        )
    }

    private fun getHuggPickDummy() : List<InfoItemVo>{
        return listOf(
            InfoItemVo(
                url = "https://blog.naver.com/foregg-/223473055862",
                tags = listOf("#난임과 음주","#시험관 음주","#남편 술"),
                image = "https://drive.google.com/uc?export=download&id=10U2AYfUg8MrMJ9ZrprJCwb9oIeppP_sg"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223448519504",
                tags = listOf("#난임에 좋은 음식","#식단관리","#항산화음식"),
                image = "https://drive.google.com/uc?export=download&id=1fiRWO_MpfToL7d8ygy0Znz029SdxiyRF"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223449818880",
                tags = listOf("#난임영앙제추천","#코엔자임","#코큐텐"),
                image = "https://drive.google.com/uc?export=download&id=1KmDifedZZAZ8NE5s2wb0DeGMMoJAh9Ri"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223451740757",
                tags = listOf("#난임스트레스","#스트레스관리법"),
                image = "https://drive.google.com/uc?export=download&id=13MNku8oWy7JcKU7LWsGUmVscCwlXG-N2"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223465584034",
                tags = listOf("#태아위협","#미세플라스틱","#줄이는 방법"),
                image = "https://drive.google.com/uc?export=download&id=1vraXf7UJpoBiVRs6q3GnfQw3S5vxvlla"
            ),
            InfoItemVo(
                url = "https://m.blog.naver.com/foregg-/223452355860",
                tags = listOf("#난임영양제","#비타민D"),
                image = "https://drive.google.com/uc?export=download&id=1XwlvvjQBPFCxpx8L20iFtCbxzSxicvmB"
            )
        )
    }
}