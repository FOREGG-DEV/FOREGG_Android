package com.foregg.presentation.ui.main.account.bottomSheet

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.foregg.presentation.R
import com.foregg.presentation.databinding.BottomSheetAccountDatePickerBinding
import com.foregg.presentation.util.TimeFormatter
import com.foregg.presentation.util.serializable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Calendar

class AccountDatePickBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val KEY_START_DAY = "key_start_day"
        private const val KEY_END_DAY = "key_end_day"

        fun newInstance(
            startDay: String,
            endDay: String,
            confirmClick: (String, String) -> Unit
        ) = AccountDatePickBottomSheet().apply {
            this.confirmClick = confirmClick
            arguments = Bundle().apply {
                putSerializable(KEY_START_DAY, startDay)
                putSerializable(KEY_END_DAY, endDay)
            }
        }
    }

    private var confirmClick: ((String, String) -> Unit)? = null
    private val startDay: String by lazy {
        arguments?.serializable(KEY_START_DAY) ?: ""
    }
    private val endDay: String by lazy {
        arguments?.serializable(KEY_END_DAY) ?: ""
    }
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val today = TimeFormatter.getToday()
    private var passedStartDay : String = ""
    private var passedEndDay : String = ""

    private val calendar = Calendar.getInstance()
    private val datePickerDialog : DatePickerDialog by lazy { DatePickerDialog(requireContext(),
        R.style.DatePickerStyle,
        null,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ) }

    private val binding: BottomSheetAccountDatePickerBinding by lazy {
        BottomSheetAccountDatePickerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.behavior.apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            textStartDay.text = startDay
            textEndDay.text = endDay
            setListener()
        }
    }

    private fun setListener(){
        binding.apply {

            imgBtnCancel.setOnClickListener {
                dismiss()
            }

            constraintLayoutBtnOneMonth.setOnClickListener {
                oneMonthBtnClicked()
                passedStartDay = TimeFormatter.getPreviousMonthDate()
                passedEndDay = today
            }

            constraintLayoutBtnThreeMonth.setOnClickListener {
                threeMonthBtnClicked()
                passedStartDay = TimeFormatter.getPreviousThreeMonthDate()
                passedEndDay = today
            }

            constraintLayoutBtnLastMonth.setOnClickListener {
                lastMonthBtnClicked()
                val currentMonthStart = LocalDate.now().withDayOfMonth(1)
                val previousMonthEnd = currentMonthStart.minusDays(1)
                val previousMonthStart = previousMonthEnd.withDayOfMonth(1)

                passedStartDay = previousMonthStart.format(formatter)
                passedEndDay = previousMonthEnd.format(formatter)
            }

            constraintLayoutBtnCustomMonth.setOnClickListener {
                customMonthBtnClicked()
                constraintLayoutCustomDatePicker.visibility = View.VISIBLE
            }

            textStartDay.setOnClickListener {
                datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
                    val formattedMonth = String.format("%02d", month + 1)
                    val formattedDay = String.format("%02d", day)
                    passedStartDay = "$year-$formattedMonth-$formattedDay"
                    textStartDay.text = passedStartDay
                    isAfterEndDay()
                }
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
            }

            textEndDay.setOnClickListener {
                datePickerDialog.setOnDateSetListener { datePicker, year, month, day ->
                    val formattedMonth = String.format("%02d", month + 1)
                    val formattedDay = String.format("%02d", day)
                    passedEndDay = "$year-$formattedMonth-$formattedDay"
                    textEndDay.text = passedEndDay
                    isBeforeStartDay()
                }
                datePickerDialog.show()
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
            }

            textBtnConfirm.setOnClickListener {
                confirmClick?.invoke(passedStartDay, passedEndDay)
                dismiss()
            }
        }
    }

    private fun isAfterEndDay(){
        val start = LocalDate.parse(passedStartDay, formatter)
        val end = LocalDate.parse(binding.textEndDay.text, formatter)
        if(start.isAfter(end)) {
            binding.textEndDay.text = passedStartDay
            passedEndDay = passedStartDay
        }
    }

    private fun isBeforeStartDay(){
        val end = LocalDate.parse(passedEndDay, formatter)
        val start = LocalDate.parse(binding.textStartDay.text, formatter)
        if(end.isBefore(start)) {
            binding.textStartDay.text = passedEndDay
            passedStartDay = passedEndDay
        }
    }

    private fun oneMonthBtnClicked(){
        binding.apply {
            constraintLayoutBtnOneMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_main3_stroke_gs_30_radius_4)
            constraintLayoutBtnThreeMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnLastMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnCustomMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)

            textOneMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_90))
            textThreeMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textLastMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textCustomMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            constraintLayoutCustomDatePicker.visibility = View.INVISIBLE
        }
    }

    private fun threeMonthBtnClicked(){
        binding.apply {
            constraintLayoutBtnThreeMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_main3_stroke_gs_30_radius_4)
            constraintLayoutBtnOneMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnLastMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnCustomMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)

            textThreeMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_90))
            textOneMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textLastMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textCustomMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            constraintLayoutCustomDatePicker.visibility = View.INVISIBLE
        }
    }

    private fun lastMonthBtnClicked(){
        binding.apply {
            constraintLayoutBtnLastMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_main3_stroke_gs_30_radius_4)
            constraintLayoutBtnOneMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnThreeMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnCustomMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)

            textLastMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_90))
            textOneMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textThreeMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textCustomMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            constraintLayoutCustomDatePicker.visibility = View.INVISIBLE
        }
    }

    private fun customMonthBtnClicked(){
        binding.apply {
            constraintLayoutBtnCustomMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_main3_stroke_gs_30_radius_4)
            constraintLayoutBtnOneMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnThreeMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)
            constraintLayoutBtnLastMonth.setBackgroundResource(R.drawable.bg_rectangle_filled_white_stroke_gs_30_radius_4)

            textCustomMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_90))
            textOneMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textThreeMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
            textLastMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gs_70))
        }
    }
}
