package com.hugg.presentation.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hugg.presentation.R
import com.hugg.presentation.databinding.CommonToastBinding

object ForeggToast {

    private const val MARGIN_BOTTOM = 72

    fun createToast(context: Context, message: String, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: CommonToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.common_toast, null, false)
        binding.textMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }

    fun createToast(context: Context, message: Int, length : Int = Toast.LENGTH_LONG): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: CommonToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.common_toast, null, false)
        binding.textMessage.setText(message)

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }
}