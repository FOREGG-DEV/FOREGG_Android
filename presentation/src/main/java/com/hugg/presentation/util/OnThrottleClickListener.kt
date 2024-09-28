package com.hugg.presentation.util

import android.view.View
import androidx.databinding.BindingAdapter

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300L,
) : View.OnClickListener {

    private var clicked = false
    override fun onClick(v: View?) {
        if (!clicked) {
            clicked = true
            v?.run {
                postDelayed(
                    { clicked = false },
                    interval,
                )
                clickListener.onClick(v)
            }
        }
    }
}

fun View.onThrottleClick(
    onClick: (v: View) -> Unit,
) {
    val listener = View.OnClickListener { onClick(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

@BindingAdapter("app:onThrottleClick")
fun setOnThrottleClickListener(view: View, listener: View.OnClickListener?) {
    listener?.let {
        view.setOnClickListener(OnThrottleClickListener(it))
    }
}