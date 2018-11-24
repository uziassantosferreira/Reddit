package com.uziassantosferreira.presentation.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.GetPostByCommunity
import com.uziassantosferreira.presentation.data.NetworkState
import com.uziassantosferreira.presentation.mapper.PresentationPostMapper
import com.uziassantosferreira.presentation.model.Post
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class PostsDataSource(
    private val getPostByCommunity: GetPostByCommunity,
    private val compositeDisposable: CompositeDisposable)
        : PageKeyedDataSource<String, Post>() {


    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, {}))
        }
    }



    override fun loadInitial(params: LoadInitialParams<String>,
                             callback: LoadInitialCallback<String, Post>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        //get the initial users from the api
        compositeDisposable.add(getPostByCommunity
            .executeUseCase(GetPostByCommunityRequestValue("r/Android"))
            .subscribe({ result ->
            // clear retry since last request succeeded
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
                val posts = PresentationPostMapper.transformFromList(result.second)
                val pagination = result.first
            callback.onResult(posts, null, pagination.nextPage)
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadInitial(params, callback) })
            val error = NetworkState.error(throwable.message)
            // publish the error
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<String>,
                           callback: LoadCallback<String, Post>) {
        networkState.postValue(NetworkState.LOADING)

        //get the users from the api after id
        compositeDisposable.add(getPostByCommunity
            .executeUseCase(GetPostByCommunityRequestValue("r/Android", params.key))
            .subscribe({ result ->
            // clear retry since last request succeeded
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
                val posts = PresentationPostMapper.transformFromList(result.second)
                val pagination = result.first
                callback.onResult(posts, pagination.nextPage)
        }, { throwable ->
            // keep a Completable for future retry
            setRetry(Action { loadAfter(params, callback) })
            // publish the error
            networkState.postValue(NetworkState.error(throwable.message))
        }))
    }

    override fun loadBefore(params: LoadParams<String>,
                            callback: LoadCallback<String, Post>) {
        //Ignore
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) {
            null
        } else {
            Completable.fromAction(action)
        }
    }
}