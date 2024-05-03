package com.foregg.presentation.ui.main.calendar.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.foregg.domain.model.enums.RecordType
import com.foregg.presentation.R
import com.foregg.presentation.databinding.DialogCreateScheduleBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CreateScheduleDialog @Inject constructor(@ActivityContext private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(binding.root)
    }

    private val binding: DialogCreateScheduleBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_create_schedule, null, false)
    }

    private var dialog: AlertDialog? = null
    fun show() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    private fun dismiss() {
        val parentViewGroup = binding.root.parent as? ViewGroup
        parentViewGroup?.removeView(binding.root)
        dialog?.dismiss()
    }

    fun onClickButton(onClickListener: (view: View, type : RecordType) -> (Unit)): CreateScheduleDialog {
        binding.apply {
            constraintLayoutMedicine.setOnClickListener { view ->
                onClickListener(view, RecordType.MEDICINE)
                dismiss()
            }
            constraintLayoutInjection.setOnClickListener { view ->
                onClickListener(view, RecordType.INJECTION)
                dismiss()
            }
            constraintLayoutHospital.setOnClickListener { view ->
                onClickListener(view, RecordType.HOSPITAL)
                dismiss()
            }
            constraintLayoutEtc.setOnClickListener { view ->
                onClickListener(view, RecordType.ETC)
                dismiss()
            }
        }
        return this
    }
}