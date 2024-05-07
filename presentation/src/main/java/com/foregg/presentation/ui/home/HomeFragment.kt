package com.foregg.presentation.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentHomeBinding
import com.foregg.presentation.ui.home.adapter.HomeTodayScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    private val todayScheduleAdapter = HomeTodayScheduleAdapter()
//    private lateinit var todayDate: String
//    private lateinit var userName: String
    private var todayDate: String = LocalDate.now().toString()
    private var userName: String = "임시 닉네임"
    private lateinit var todayScheduleList: List<HomeRecordResponseVo>


    override fun initView() {
        val (month, day) = extractMonthAndDay(todayDate)

        binding.apply {
            vm = viewModel
            val formattedText = getString(R.string.today_schedule_format, userName, month.toString(), day.toString())
            todayScheduleList = viewModel.uiState.todayScheduleList.value
            textTodaySchedule.text = formattedText
            todayScheduleViewPager.adapter = todayScheduleAdapter

            if (todayScheduleList.isEmpty()) {
                todayScheduleEmptyText.visibility = View.VISIBLE
            } else {
                todayScheduleEmptyText.visibility = View.GONE
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todayDate.collect {
                    todayDate = it
                }
            }
            launch {
                viewModel.uiState.userName.collect {
                    userName = it
                }
            }
            launch {
                viewModel.uiState.todayScheduleList.collect {
                    todayScheduleList = it
                    todayScheduleAdapter.submitList(todayScheduleList)
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