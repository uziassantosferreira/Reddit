package com.uziassantosferreira.presentation.exception

sealed class Failure {
    object Generic: Failure()
    object NoInternet: Failure()
    object RequestCanceled: Failure()
    object ConnectionTimeout: Failure()
    object EmptyList : Failure()
}