package com.foregg.presentation.ui.main.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentHomeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.adapter.HomeChallengeAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeIntroductionAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeTodayScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    private val homeIntroductionAdapter = HomeIntroductionAdapter()
    private var position = 0

    private val todayScheduleAdapter : HomeTodayScheduleAdapter by lazy {
        HomeTodayScheduleAdapter(object : HomeTodayScheduleAdapter.HomeTodayScheduleDelegate {
            override fun onClickRecordTreatment(id: Long, recordType: RecordType) {
                goToCreateEditSchedule(id, recordType)
            }

            override fun calculateNearestSchedulePosition(list: List<HomeRecordResponseVo>): Int {
                return viewModel.calculatePosition(list)
            }
        })
    }

    private val homeChallengeAdapter: HomeChallengeAdapter by lazy {
        HomeChallengeAdapter( object : HomeChallengeAdapter.HomeChallengeDelegate {
            override fun showDialog(id: Long, successDaysCount : Int) {
                showCompleteDialog(id, successDaysCount)
            }

            override fun deleteComplete(id: Long) {
                viewModel.deleteCompleteChallenge(id)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            todayScheduleViewPager.adapter = todayScheduleAdapter
            todayScheduleViewPager.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            todayScheduleViewPager.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View?,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    updatePosition()
                    todayScheduleViewPager.removeOnLayoutChangeListener(this)
                }
            })
            challengeRecyclerView.adapter = homeChallengeAdapter
            challengeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            advertiseViewPager.adapter = homeIntroductionAdapter
            indicatorView.attachTo(advertiseViewPager)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todayScheduleList.collect {
                    if (it.isNotEmpty()) todayScheduleAdapter.submitList(it as MutableList<HomeRecordResponseVo>?)
                    updatePosition()
                }
            }
            launch {
                viewModel.uiState.challengeList.collect {
                    homeChallengeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.uiState.homeIntroductionItemList.collect {
                    homeIntroductionAdapter.submitList(it)
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
            HomeEvent.GoToDailyRecordEvent -> goToDailyRecord()
            HomeEvent.GoToCalendarEvent -> goToCalendar()
            HomeEvent.GoToCreateEditScheduleEvent -> goToCreateEditSchedule(viewModel.uiState.medicalRecordId.value, RecordType.HOSPITAL)
            is HomeEvent.ShowWeekEndDialog -> if(event.isSuccess) showSuccessChallengeDialog() else showFailChallengeDialog()
        }
    }

    private fun goToChallenge() {
        val action = HomeFragmentDirections.actionHomeToChallege()
        findNavController().navigate(action)
    }

    private fun goToDailyRecord() {
        val action = HomeFragmentDirections.actionHomeToDailyRecord()
        findNavController().navigate(action)
    }

    private fun showCompleteDialog(id: Long, successDaysCount : Int) {
        dialog
            .setTitle(R.string.home_challenge_complete_dialog_text)
            .setPositiveButton(R.string.word_yes) {
                viewModel.completeChallenge(id, successDaysCount)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                dialog.dismiss()
            }
            .show()
    }

    private fun showFailChallengeDialog() {
        dialog
            .showOnlyPositiveBtn()
            .setTitle(R.string.challenge_fail_dialog)
            .setPositiveButton(R.string.challenge_dialog_positive_btn) {
                dialog.dismiss()
            }
            .show()
    }

    private fun showSuccessChallengeDialog() {
        dialog
            .showOnlyPositiveBtn()
            .setTitle(R.string.challenge_success_dialog)
            .setPositiveButton(R.string.challenge_dialog_positive_btn) {
                dialog.dismiss()
            }
            .show()
    }

    private fun goToCreateEditSchedule(id: Long, recordType: RecordType) {
        val action = HomeFragmentDirections.actionHomeToCreateEditSchedule(id = id, type = CalendarType.EDIT, scheduleType = recordType, isHome = true)
        findNavController().navigate(action)
    }

    private fun goToCalendar() {
        val action = HomeFragmentDirections.actionHomeToCalendar()
        findNavController().navigate(action)
    }

    private fun updatePosition() {
        position = viewModel.calculatePosition(viewModel.uiState.todayScheduleList.value)
        if (position == -1) position = viewModel.uiState.todayScheduleList.value.size - 1
        (binding.todayScheduleViewPager.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }
}