package com.example.termproject

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.DatePicker

// 상하 스크롤 시 DatePicker와 스크롤 뷰의 충돌을 막기 위한 클래스
class CustomDatePicker : DatePicker {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(ev)
    }
}
