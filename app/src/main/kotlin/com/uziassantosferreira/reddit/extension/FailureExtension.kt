package com.uziassantosferreira.reddit.extension

import android.content.Context
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.reddit.R

fun Failure.getMessage(context: Context): String = when(this){
    Failure.Generic -> context.getString(R.string.label_generic_error)
    Failure.NoInternet -> context.getString(R.string.label_network_error)
    Failure.RequestCanceled -> context.getString(R.string.label_network_error)
    Failure.ConnectionTimeout -> context.getString(R.string.label_network_error)
    Failure.EmptyList -> context.getString(R.string.label_empty_view)
}