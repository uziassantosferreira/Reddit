package com.uziassantosferreira.data.errors

import com.uziassantosferreira.domain.exception.DomainThrowable
import com.uziassantosferreira.domain.exception.Failure
import io.reactivex.Flowable
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorHandlerTest {

    @Test
    fun `should be get error no connection`() {
        Flowable.error<String>(UnknownHostException())
            .compose(NetworkingErrorHandler())
            .test()
            .assertError(DomainThrowable(Failure.NoInternet))
            .assertNoValues()
            .assertNotComplete()
    }

    @Test
    fun `should be get error request canceled`() {
        Flowable.error<String>(IOException("Canceled"))
            .compose(NetworkingErrorHandler())
            .test()
            .assertError(DomainThrowable(Failure.RequestCanceled))
            .assertNoValues()
            .assertNotComplete()
    }

    @Test
    fun `should be get error connection timeout `() {
        Flowable.error<String>(SocketTimeoutException())
            .compose(NetworkingErrorHandler())
            .test()
            .assertError(DomainThrowable(Failure.ConnectionTimeout))
            .assertNoValues()
            .assertNotComplete()
    }

}