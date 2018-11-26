package com.uziassantosferreira.presentation.exception

sealed class Failure {
    object Generic: Failure()

    object NoInternet: Failure()
    object RequestCanceled: Failure()
    object ConnectionTimeout: Failure()

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Failure.Generic -> true
            is Failure.NoInternet -> true
            is Failure.RequestCanceled -> true
            is Failure.ConnectionTimeout -> true
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}