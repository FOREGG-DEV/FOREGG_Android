package com.foregg.presentation.ui.dailyRecord.bottomSheet

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
            frameLayoutDelete.setOnClickListener { onClickBtnDelete() }
            frameLayoutEdit.setOnClickListener {
                onClickBtnEdit()
                dismiss()
            }
        }
    }
}