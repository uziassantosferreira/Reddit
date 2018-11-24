package com.uziassantosferreira.presentation.model

sealed class Failure {
    object Generic: Failure()
}