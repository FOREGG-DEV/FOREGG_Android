package com.foregg.presentation.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.foregg.presentation.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dailog)
        setCancelable(false)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}