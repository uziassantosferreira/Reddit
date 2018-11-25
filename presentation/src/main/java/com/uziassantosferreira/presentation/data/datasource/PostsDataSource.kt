package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.NetworkState
import com.uziassantosferreira.presentation.exception.ErrorHandler
import com.uziassantosferreira.presentation.exception.PresentationThrowable
import com.uziassantosferreira.presentation.mapper.PresentationPostMapper
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.presentation.util.UseCaseHandler
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
class PostsDataSource(
    private val getPostByCommunity: UseCase<GetPostByCommunityRequestValue, Pair<Pagination, List<com.uziassantosferreira.domain.model.Post>>>,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<String, Post>() {

    companion object {
        private const val COMMUNITY = "r/Android"
    }

    val networkState = MutableLiveData<NetworkState>()

    private var retry: (() -> Any)? = null

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Post>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(getPosts()
            .subscribe({ result ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.second, null, result.first)
            }, { throwable ->
                handleError(throwable) { loadInitial(params, callback) }
            })
        )
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, Post>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(getPosts(params.key)
            .subscribe({ result ->
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.second, result.first)
            }, { throwable ->
                handleError(throwable) { loadAfter(params, callback) }
            })
        )
    }

    private fun transformToPresentation(pair: Pair<Pagination,
            List<com.uziassantosferreira.domain.model.Post>>): Pair<String, List<Post>> =
        pair.first.nextPage to PresentationPostMapper.transformFromList(pair.second)

    private fun getPosts(nextPage: String = ""): Flowable<Pair<String, List<Post>>> =
        UseCaseHandler.execute(getPostByCommunity,
            GetPostByCommunityRequestValue(COMMUNITY, nextPage))
            .map(::transformToPresentation)
            .compose(ErrorHandler())

    @Suppress("UNUSED_EXPRESSION")
    private fun handleError(throwable: Throwable?, functionCall: () -> Unit) {
        retry = functionCall
        val error = NetworkState.error((throwable as PresentationThrowable).failure)
        networkState.postValue(error)
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Post>
    ) {
        //Ignore
    }

}