package com.foregg.presentation.ui.main.calendar.createOrEdit

import android.app.DatePickerDialog
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.CalendarDatePickerType
import com.foregg.domain.model.enums.CalendarTabType
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.vo.CreateScheduleTimeVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateEditScheduleBinding
import com.foregg.presentation.ui.common.spinner.CommonSpinnerAdapter
import com.foregg.presentation.ui.main.calendar.createOrEdit.adapter.SettingTimeAdapter
import com.foregg.presentation.ui.main.calendar.dialog.CreateScheduleDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class CreateEditScheduleFragment : BaseFragment<FragmentCreateEditScheduleBinding, CreateEditSchedulePageState, CreateEditScheduleViewModel>(
    FragmentCreateEditScheduleBinding::inflate
) {

    @Inject
    lateinit var createScheduleDialog: CreateScheduleDialog

    private val createEditScheduleFragmentArgs : CreateEditScheduleFragmentArgs by navArgs()

    override val viewModel: CreateEditScheduleViewModel by viewModels()

    private val settingTimeAdapter : SettingTimeAdapter by lazy {
        SettingTimeAdapter(object : SettingTimeAdapter.SettingTimeDelegate{
            override fun updateItemList(list: List<CreateScheduleTimeVo>) {
                viewModel.updateSetTime(list)
            }
        })
    }

    private val spinnerAdapter : CommonSpinnerAdapter by lazy {
        CommonSpinnerAdapter(object : CommonSpinnerAdapter.CommonSpinnerDelegate{
            override fun onClickType(type: String) {
                viewModel.updateClassificationDetail(type)
            }
        })
    }

    private val calendar = Calendar.getInstance()
    private val datePickerDialog : DatePickerDialog by lazy { DatePickerDialog(requireContext(),
        R.style.DatePickerStyle,
        null,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ) }

    private var canUpdateTime : Boolean = true

    override fun initView() {
        binding.apply {
            vm = viewModel

            includeLayoutCalendarSchedule.recyclerViewTimeSetting.apply {
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
                adapter = settingTimeAdapter
            }

            includeLayoutCalendarSchedule.recyclerSpinner.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = spinnerAdapter
            }

            bindView()
            viewModel.setViewType(createEditScheduleFragmentArgs)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.setTimeList.collect {
                    if(canUpdateTime && it.isNotEmpty() && createEditScheduleFragmentArgs.type == CalendarType.EDIT) {
                        settingTimeAdapter.setData(it)
                        canUpdateTime = false
                    }
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as CreateEditScheduleEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: CreateEditScheduleEvent){
        when(event){
            CreateEditScheduleEvent.ShowSelectScheduleDialog -> showSelectScheduleDialog()
            is CreateEditScheduleEvent.ShowDatePickerDialogEvent -> showDatePickerDialog(event.type)
            CreateEditScheduleEvent.GoToBackEvent -> findNavController().popBackStack()
        }
    }

    private fun showSelectScheduleDialog(){
        createScheduleDialog.onClickButton { _, type ->
            viewModel.updateClassification(type)
        }.show()
    }

    private fun showDatePickerDialog(type : CalendarDatePickerType){
        datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
            val formattedMonth = String.format("%02d", month + 1)
            val formattedDay = String.format("%02d", day)
            viewModel.setDate(type, "$year-$formattedMonth-$formattedDay")
        }
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
    }

    private fun bindView(){
        if(createEditScheduleFragmentArgs.type == CalendarType.EDIT
            && createEditScheduleFragmentArgs.scheduleType == RecordType.HOSPITAL) bindTab()
        if(createEditScheduleFragmentArgs.type == CalendarType.CREATE)
            settingTimeAdapter.setData(listOf(CreateScheduleTimeVo()).toMutableList()
        )
        if(createEditScheduleFragmentArgs.isProfile) binding.imgBtnComplete.visibility = View.GONE
        spinnerAdapter.submitList(resources.getStringArray(R.array.injection_list).toList())
        canUpdateTime = true
    }

    private fun bindTab(){
        binding.customTabBar.apply {
            if(createEditScheduleFragmentArgs.isHome) {
                viewModel.updateTabType(CalendarTabType.MEDICAL_RECORD)
                setRightBtnClickedBackground()
            }
            else {
                viewModel.updateTabType(CalendarTabType.SCHEDULE)
                setLeftBtnClickedBackground()
            }
            leftBtnClicked { viewModel.updateTabType(CalendarTabType.SCHEDULE) }
            rightBtnClicked { viewModel.updateTabType(CalendarTabType.MEDICAL_RECORD) }
        }
    }
}