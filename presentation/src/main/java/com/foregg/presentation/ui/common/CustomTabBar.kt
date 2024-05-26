package com.foregg.presentation.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.foregg.presentation.R
import com.foregg.presentation.databinding.CustomTabBarBinding


class CustomTabBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomTabBarBinding
    private val tabCount: Int
    private val leftTabText: String?
    private val middleTabText: String?
    private val rightTabText: String?

    private var isLeftTabClicked : Boolean = true
    private var isMiddleTabClicked : Boolean = false
    private var isRightTabClicked : Boolean = false

    companion object{
        const val TWO_TAB = 2
    }

    val leftTab : AppCompatButton
        get() = binding.btnLeft

    val middleTab : AppCompatButton
        get() = binding.btnMiddle

    val rightTab : AppCompatButton
        get() = binding.btnRight

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_tab_bar, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_tab_bar, defStyleAttr, 0)

        tabCount = typedArray.getInteger(R.styleable.custom_tab_bar_tabCount, TWO_TAB)
        leftTabText = typedArray.getString(R.styleable.custom_tab_bar_leftText)
        middleTabText = typedArray.getString(R.styleable.custom_tab_bar_middleText)
        rightTabText = typedArray.getString(R.styleable.custom_tab_bar_rightText)
        if(tabCount == TWO_TAB){
            middleTab.visibility = View.GONE
        }

        binding.apply {
            leftTab.text = leftTabText
            if(tabCount != TWO_TAB) middleTab.text = middleTabText
            rightTab.text = rightTabText
        }
        typedArray.recycle()
    }

    fun leftBtnClicked(onClickListener: (view: View) -> (Unit)){
        leftTab.setOnClickListener {
            if(isLeftTabClicked) return@setOnClickListener
            setLeftBtnClickedBackground()
            isLeftTabClicked = true
            isMiddleTabClicked = false
            isRightTabClicked = false
            onClickListener(it)
        }
    }

    fun middleBtnClicked(onClickListener: (view: View) -> (Unit)){
        middleTab.setOnClickListener {
            if(isMiddleTabClicked) return@setOnClickListener
            setMiddleBtnClickedBackground()
            isLeftTabClicked = false
            isMiddleTabClicked = true
            isRightTabClicked = false
            onClickListener(it)
        }
    }

    fun rightBtnClicked(onClickListener: (view: View) -> (Unit)){
        rightTab.setOnClickListener {
            if(isRightTabClicked) return@setOnClickListener
            setRightBtnClickedBackground()
            isLeftTabClicked = false
            isMiddleTabClicked = false
            isRightTabClicked = true
            onClickListener(it)
        }
    }

    fun setLeftBtnClickedBackground() {
        leftTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        middleTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
        rightTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
    }

    fun setMiddleBtnClickedBackground(){
        leftTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
        middleTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        rightTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
    }

    fun setRightBtnClickedBackground(){
        leftTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
        middleTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        }
        rightTab.apply {
            setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }
}