package com.uziassantosferreira.presentation.exception

import com.uziassantosferreira.domain.exception.DomainThrowable
import com.uziassantosferreira.domain.exception.Failure
import com.uziassantosferreira.presentation.exception.Failure as PresentationFailure
import io.reactivex.Flowable
import org.junit.Test

class ErrorHandlerTest {

    @Test
    fun `should be get error no connection`() {
        assertThrowable(DomainThrowable(Failure.NoInternet),
            PresentationThrowable(PresentationFailure.NoInternet))
    }

    @Test
    fun `should be get error request canceled`() {
        assertThrowable(DomainThrowable(Failure.RequestCanceled),
            PresentationThrowable(PresentationFailure.RequestCanceled))
    }

    @Test
    fun `should be get error connection timeout `() {
        assertThrowable(DomainThrowable(Failure.ConnectionTimeout),
            PresentationThrowable(PresentationFailure.ConnectionTimeout))
    }

    @Test
    fun `should be get error generic `() {
        assertThrowable(DomainThrowable(Failure.Generic), PresentationThrowable(PresentationFailure.Generic))
    }

    private fun assertThrowable(actual: Throwable, expected: Throwable){
        Flowable.error<String>(actual)
            .compose(ErrorHandler())
            .test()
            .assertError(expected)
            .assertNoValues()
            .assertNotComplete()
    }
}