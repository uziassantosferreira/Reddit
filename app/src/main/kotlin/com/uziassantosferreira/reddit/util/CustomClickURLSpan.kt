package com.uziassantosferreira.reddit.util

import android.text.style.URLSpan
import android.view.View


class CustomClickURLSpan(url: String) : URLSpan(url) {
    private var mOnClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        mOnClickListener = onClickListener
    }

    override fun onClick(widget: View) {
        if (mOnClickListener == null) {
            super.onClick(widget)
        } else {
            mOnClickListener?.onClick(widget, url)
        }
    }

    interface OnClickListener {
        fun onClick(view: View, url: String)
    }
}
