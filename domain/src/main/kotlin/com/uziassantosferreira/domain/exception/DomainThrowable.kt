package com.uziassantosferreira.domain.exception

data class DomainThrowable(val failure: Failure = Failure.Generic): Throwable()