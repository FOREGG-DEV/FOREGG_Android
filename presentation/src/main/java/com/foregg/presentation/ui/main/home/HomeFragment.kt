package com.foregg.presentation.ui.main.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    override fun initView() {
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

        viewModel.initScheduleStates()
    }

    private fun sortEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.GoToChallengeEvent -> goToChallenge()
            HomeEvent.GoToDailyRecordEvent -> TODO()
        }
    }

    private fun goToChallenge() {
        val action = HomeFragmentDirections.actionHomeToChallege()
        findNavController().navigate(action)
    }
}