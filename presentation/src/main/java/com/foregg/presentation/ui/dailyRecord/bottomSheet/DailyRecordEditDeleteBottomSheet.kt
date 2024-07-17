package com.foregg.presentation.ui.dailyRecord.bottomSheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foregg.presentation.databinding.BottomSheetDialogDailyRecordEditDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DailyRecordEditDeleteBottomSheet(
    private val onClickBtnDelete: () -> Unit,
    private val onClickBtnEdit: () -> Unit
): BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogDailyRecordEditDeleteBinding by lazy {
        BottomSheetDialogDailyRecordEditDeleteBinding.inflate(layoutInflater)
    }
    private var dismissListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            frameLayoutDelete.setOnClickListener {
                onClickBtnDelete()
                dismiss()
            }
            frameLayoutEdit.setOnClickListener {
                onClickBtnEdit()
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke()
    }

    fun setOnDismissListener(listener: () -> Unit) {
        dismissListener = listener
    }
}