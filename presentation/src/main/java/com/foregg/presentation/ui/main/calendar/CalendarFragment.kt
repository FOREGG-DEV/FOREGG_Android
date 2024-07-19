package com.foregg.presentation.ui.main.calendar

import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.NotificationType
import com.foregg.domain.model.enums.RecordType
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCalendarBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.calendar.adapter.CalendarDayAdapter
import com.foregg.presentation.ui.main.calendar.adapter.ScheduleAdapter
import com.foregg.presentation.ui.main.calendar.dialog.CreateScheduleDialog
import com.foregg.presentation.util.ForeggNotification
import com.foregg.presentation.util.ForeggToast
import com.foregg.presentation.util.px
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarPageState, CalendarViewModel>(
    FragmentCalendarBinding::inflate
) {

    @Inject
    lateinit var createScheduleDialog: CreateScheduleDialog

    @Inject
    lateinit var commonDialog: CommonDialog

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object{
        const val ITEM_SPAN_COUNT = 7
        const val BOTTOM_NAV_HEIGHT = 58
        const val MARGIN_FROM_CALENDAR = 24
    }

    override val viewModel: CalendarViewModel by viewModels()

    private val calendarDayAdapter : CalendarDayAdapter by lazy {
        CalendarDayAdapter(object : CalendarDayAdapter.CalendarDayDelegate{
            override fun onClickDay(day: String) {
                viewModel.onClickDay(day)
            }
        })
    }

    private val scheduleAdapter : ScheduleAdapter by lazy {
        ScheduleAdapter(object : ScheduleAdapter.ScheduleDelegate{
            override fun onClickDetail(id: Long, type : RecordType) {
                goToCreateSchedule(CalendarType.EDIT, type, id = id)
            }

            override fun onClickDelete(id: Long) {
                showDeleteDialog(id)
            }
        })
    }

    override fun initView() {
        ForeggNotification.updateNoty(requireContext(), NotificationType.CALENDAR, false)
        binding.apply {
            vm = viewModel
            includeLayoutCalendar.recyclerViewCalendar.apply {
                layoutManager = GridLayoutManager(root.context, ITEM_SPAN_COUNT)
                itemAnimator = null
                adapter = calendarDayAdapter
                adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                        super.onItemRangeChanged(positionStart, itemCount)
                        postLayoutRunnable()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)
                        postLayoutRunnable()
                    }
                })
            }

            recyclerViewSchedule.apply {
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
                adapter = scheduleAdapter
            }
            initializePersistentBottomSheet()
            viewModel.setCalendar()
            postLayoutRunnable()
        }
    }

    private fun postLayoutRunnable() {
        binding.includeLayoutCalendar.recyclerViewCalendar.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout(){
                val parentHeight = resources.displayMetrics.heightPixels
                val bottomY = binding.constraintLayoutCalendar.bottom
                val peekHeight = parentHeight - bottomY - BOTTOM_NAV_HEIGHT.px - MARGIN_FROM_CALENDAR.px
                bottomSheetBehavior.peekHeight = peekHeight
                binding.includeLayoutCalendar.recyclerViewCalendar.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.calendarDayList.collect {
                    if(it.isNotEmpty()) {
                        calendarDayAdapter.submitList(it)
                    }
                }
            }
            launch {
                viewModel.uiState.scheduleList.collect {
                    scheduleAdapter.submitList(it)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as CalendarEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: CalendarEvent){
        when(event){
            CalendarEvent.CreateScheduleEvent -> showCreateScheduleDialog()
            CalendarEvent.ErrorDelete -> ForeggToast.createToast(requireContext(), R.string.toast_error_no_exist_schedule, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCreateScheduleDialog(){
        createScheduleDialog.onClickButton { _, type ->
            goToCreateSchedule(CalendarType.CREATE, type)
        }.show()
    }

    private fun goToCreateSchedule(pageType : CalendarType, type : RecordType = RecordType.MEDICINE, id : Long = -1){
        val action = CalendarFragmentDirections.actionCalendarToCreateEdit(type = pageType, scheduleType = type, id = id)
        findNavController().navigate(action)
    }

    private fun initializePersistentBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.constraintLayoutBottomSheet)
        binding.apply {
            val parentHeight = resources.displayMetrics.heightPixels
            recyclerViewSchedule.layoutParams.height = parentHeight / 2
            recyclerViewSchedule.requestLayout()
        }
    }

    private fun showDeleteDialog(id : Long){
        commonDialog
            .setTitle(R.string.calendar_delete_schedule)
            .setPositiveButton(R.string.word_yes ) {
                viewModel.onClickDelete(id)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                commonDialog.dismiss()
            }
            .show()
    }
}