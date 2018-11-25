package com.uziassantosferreira.domain.exception

sealed class Failure {
    object Generic: Failure()

    object NoInternet: Failure()
    object RequestCanceled: Failure()
    object ConnectionTimeout: Failure()
}