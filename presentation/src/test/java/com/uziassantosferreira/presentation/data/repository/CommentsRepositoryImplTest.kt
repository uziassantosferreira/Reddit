package com.uziassantosferreira.presentation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.uziassantosferreira.domain.exception.DomainThrowable
import com.uziassantosferreira.domain.model.Comment
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.requestvalue.GetCommentsByCommunityAndRemoteIdRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.datasource.CommentsDataSourceFactory
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.presentation.util.RxImmediateSchedulerRule
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import org.amshove.kluent.*
import org.junit.Rule
import org.junit.Test

class CommentsRepositoryImplTest {

    @Rule
    @JvmField
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val getComments: UseCase<GetCommentsByCommunityAndRemoteIdRequestValue,
            Pair<Pagination, List<Comment>>> = com.nhaarman.mockitokotlin2.mock()

    private val factory = CommentsDataSourceFactory(getComments)

    private val compositeDisposable = CompositeDisposable()

    private val repository = CommentsRepositoryImpl(factory, getPagedListConfig()).apply {
        setCompositeDisposable(compositeDisposable)
    }

    @Test
    fun `should get comments and expected failed`() {
        When calling getComments.run() itReturns Flowable.error(DomainThrowable())

        val listing = repository.getList("")
        getPagedList(listing)
        getNetworkState(repository.getNetworkState()).failure shouldBe Failure.Generic
    }

    @Test
    fun `should get comments and expected empty list`() {
        When calling getComments.run() itReturns Flowable.just(Pair(Pagination(), listOf()))

        val listing = repository.getList("")
        val pagedList = getPagedList(listing)

        pagedList.shouldBeEmpty()

        getNetworkState(repository.getNetworkState()).failure shouldBe Failure.EmptyList
    }

    @Test
    fun `should get comments and expected list`() {
        val nextPage = "teste"
        When calling getComments.run() itReturns Flowable.just(Pair(Pagination(nextPage = nextPage), listOf(Comment())))

        val listing = repository.getList("")
        val pagedList = getPagedList(listing)

        pagedList shouldContainAll listOf(com.uziassantosferreira.presentation.model.Comment())
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }


    @Test
    fun `should retry and expected list`() {
        When calling getComments.run() itReturns Flowable.error(DomainThrowable())
        val listing = repository.getList("")
        getPagedList(listing)
        getNetworkState(repository.getNetworkState()).status shouldBe Status.FAILED
        
        When calling getComments.run() itReturns Flowable.just(Pair(Pagination(), listOf(Comment())))
        repository.retry()
        val pagedList = getPagedList(listing)
        pagedList shouldContainAll listOf(com.uziassantosferreira.presentation.model.Comment())
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }

    @Test
    fun `should refresh and expected list`() {
        When calling getComments.run() itReturns Flowable.just(Pair(Pagination(), listOf(Comment())))
        val listing = repository.getList("")

        val pagedList = getPagedList(listing)
        pagedList shouldContainAll listOf(com.uziassantosferreira.presentation.model.Comment())
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS

        When calling getComments.run() itReturns Flowable.just(Pair(
            Pagination(), listOf(
                Comment(),
                Comment()
            )))

        repository.refresh()

        pagedList shouldContainAll listOf(
            com.uziassantosferreira.presentation.model.Comment(),
            com.uziassantosferreira.presentation.model.Comment()
        )
        getNetworkState(repository.getNetworkState()).status shouldBe Status.SUCCESS
    }

    private fun getPagedList(listing: LiveData<PagedList<com.uziassantosferreira.presentation.model.Comment>>): 
            PagedList<com.uziassantosferreira.presentation.model.Comment> {
        val observer = LoggingObserver<PagedList<com.uziassantosferreira.presentation.model.Comment>>()
        listing.observeForever(observer)
        observer.value.shouldNotBeNull()
        return observer.value!!
    }

    private fun getNetworkState(listing: LiveData<NetworkState>) : NetworkState {
        val networkObserver = LoggingObserver<NetworkState>()
        listing.observeForever(networkObserver)
        networkObserver.value.shouldNotBeNull()
        return networkObserver.value!!
    }

    private fun getPagedListConfig() =
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .build()

    private class LoggingObserver<T> : Observer<T> {
        var value : T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}