package com.foregg.presentation.ui.sign.onBoarding

import com.foregg.presentation.PageState
import kotlinx.coroutines.flow.StateFlow

data class OnboardingPageState(
    val imageList : StateFlow<List<String>>,
    val isLastPage : StateFlow<Boolean>
) : PageState