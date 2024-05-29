package com.foregg.presentation.ui.sign.onBoarding

import com.foregg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class OnboardingPageState(
    val imageList : StateFlow<List<OnboardingTutorialVo>>,
    val isLastPage : StateFlow<Boolean>
) : PageState