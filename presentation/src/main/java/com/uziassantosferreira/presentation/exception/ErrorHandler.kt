package com.uziassantosferreira.presentation.exception

import com.uziassantosferreira.domain.exception.DomainThrowable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import com.uziassantosferreira.domain.exception.Failure as domain

class ErrorHandler<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.onErrorResumeNext(Function{ this.handleError(it) })
    }

    private fun handleError(throwable: Throwable): Publisher<T> =
        Flowable.error<T>(PresentationThrowable(if (throwable is DomainThrowable) failureFrom(throwable)
        else Failure.Generic))

    private fun failureFrom(domainThrowable: DomainThrowable): Failure = when(domainThrowable.failure){
        domain.Generic -> Failure.Generic
        domain.NoInternet -> Failure.NoInternet
        domain.RequestCanceled -> Failure.RequestCanceled
        domain.ConnectionTimeout -> Failure.ConnectionTimeout
    }

}
