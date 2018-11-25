package com.uziassantosferreira.presentation.exception

data class PresentationThrowable(val failure: Failure = Failure.Generic): Throwable()