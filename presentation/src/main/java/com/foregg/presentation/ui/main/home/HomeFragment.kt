package com.foregg.presentation.ui.main.home

import android.view.View
import androidx.fragment.app.viewModels
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentHomeBinding
import com.foregg.presentation.ui.main.home.adapter.HomeTodayScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    private val todayScheduleAdapter = HomeTodayScheduleAdapter()
    private var todayDate: String = LocalDate.now().toString()

    override fun initView() {
        viewModel.initScheduleStates()

        binding.apply {
            vm = viewModel
            todayScheduleViewPager.adapter = todayScheduleAdapter
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todayScheduleList.collect {
                    todayScheduleAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as HomeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.GoToChallengeEvent -> TODO()
            HomeEvent.GoToDailyRecordEvent -> TODO()
        }
    }

    private fun extractMonthAndDay(dateString: String): Pair<Int, Int> {
        val date = LocalDate.parse(dateString)
        val month = date.monthValue
        val day = date.dayOfMonth

        return Pair(month, day)
    }
}