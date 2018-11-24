package com.uziassantosferreira.presentation.util

import com.uziassantosferreira.domain.requestvalue.RequestValues
import com.uziassantosferreira.domain.usecase.UseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object UseCaseHandler {

    fun <RV : RequestValues, T> execute(useCase: UseCase<RV, T>, values: RV? = null): Flowable<T> {
        useCase.setRequestValues(values)
        return useCase.run()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}