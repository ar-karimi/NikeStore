package com.ark.nikestore.view.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.ark.nikestore.R

class BaseToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var onBackBtnClickListener: View.OnClickListener? = null
        set(value) {
            field = value
            findViewById<View>(R.id.backBtn).setOnClickListener(onBackBtnClickListener)
        }

    init {
        inflate(context, R.layout.view_toolbar, this)

        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar)
            val title = a.getString(R.styleable.BaseToolbar_toolbar_title)
            if (!title.isNullOrEmpty())
                findViewById<TextView>(R.id.toolbarTitleTv).text = title

            a.recycle()
        }
    }
}