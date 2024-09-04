package com.hugg.presentation.ui.sign.onBoarding

import com.hugg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.hugg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class OnboardingPageState(
    val imageList : StateFlow<List<OnboardingTutorialVo>>,
    val isLastPage : StateFlow<Boolean>,
    val isFirstPage : StateFlow<Boolean>
) : PageState