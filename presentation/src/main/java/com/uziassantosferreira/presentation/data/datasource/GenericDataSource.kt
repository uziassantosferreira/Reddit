package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.RequestValues
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.exception.ErrorHandler
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.presentation.exception.PresentationThrowable
import com.uziassantosferreira.presentation.util.UseCaseHandler
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

open class GenericDataSource<Input, Response, RV: RequestValues>(
    private val useCase: UseCase<RV, Pair<Pagination, List<Input>>>,
    private val compositeDisposable: CompositeDisposable,
    private val requestValues: (pagination: Pagination) -> RV,
    private val mapper: (List<Input>) -> List<Response>
) : PageKeyedDataSource<Pagination, Response>() {

    val networkState = MutableLiveData<NetworkState>()

    private var retry: (() -> Any)? = null

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Pagination>,
        callback: LoadInitialCallback<Pagination, Response>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(useCaseCall()
            .subscribe({ result ->
                if (result.second.isEmpty()){
                    networkState.postValue(NetworkState.error(Failure.EmptyList))
                }else {
                    networkState.postValue(NetworkState.LOADED)
                }
                callback.onResult(result.second, null, if (result.first.nextPage.isEmpty()) null else result.first)
            }, { throwable ->
                handleError(throwable) { loadInitial(params, callback) }
            })
        )
    }

    override fun loadAfter(
        params: LoadParams<Pagination>,
        callback: LoadCallback<Pagination, Response>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(useCaseCall(params.key)
            .subscribe({ result ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.second, result.first)
            }, { throwable ->
                handleError(throwable) { loadAfter(params, callback) }
            })
        )
    }

    private fun useCaseCall(pagination: Pagination = Pagination()): Flowable<Pair<Pagination, List<Response>>> =
        UseCaseHandler.execute(useCase, requestValues.invoke(pagination))
            .map {
                val result = mapper.invoke(it.second)
                Pair(it.first, result)
            }
            .compose(ErrorHandler())

    @Suppress("UNUSED_EXPRESSION")
    private fun handleError(throwable: Throwable?, functionCall: () -> Unit) {
        retry = functionCall
        val error = NetworkState.error((throwable as PresentationThrowable).failure)
        networkState.postValue(error)
    }

    override fun loadBefore(
        params: LoadParams<Pagination>,
        callback: LoadCallback<Pagination, Response>
    ) {
        //Ignore
    }
}