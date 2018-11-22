package com.uziassantosferreira.domain.exception

sealed class Failure {
    object Generic: Failure()
}