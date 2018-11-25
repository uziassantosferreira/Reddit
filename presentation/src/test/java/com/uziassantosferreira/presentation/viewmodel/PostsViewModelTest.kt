package com.uziassantosferreira.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.domain.model.Pagination
import com.uziassantosferreira.domain.model.Post
import com.uziassantosferreira.domain.requestvalue.GetPostByCommunityRequestValue
import com.uziassantosferreira.domain.usecase.UseCase
import com.uziassantosferreira.presentation.data.NetworkState
import com.uziassantosferreira.presentation.data.datasource.PostsDataSourceFactory
import com.uziassantosferreira.presentation.util.RxImmediateSchedulerRule
import io.reactivex.Flowable
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class PostsViewModelTest {

    companion object {
        private val FAKE_DATE = Date(1000)
        private val FAKE_POST = Post(date = FAKE_DATE)
        private val Fake_PRESENTATION_POST = com.uziassantosferreira.presentation.model.Post(date = FAKE_DATE)
    }
    private val getPosts: UseCase<GetPostByCommunityRequestValue,
            Pair<Pagination, List<Post>>> = mock()

//    private val compositeDisposable = CompositeDisposable()
//    private val postsDataSource: PostsDataSource = PostsDataSource(getPosts, compositeDisposable)
    private val config: PagedList.Config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(1)
        .setPageSize(1)
        .setEnablePlaceholders(false)
        .build()

    private val postsDataSourceFactory = PostsDataSourceFactory(getPosts)


//    private val postsDataSourceLiveData = MutableLiveData<PostsDataSource>()
//    private val networkStateLiveData = MutableLiveData<NetworkState>()
//    private val networkState = NetworkState.LOADED


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: PostsViewModel

    @Before
    fun `set up mocks`() {
        viewModel = PostsViewModel(postsDataSourceFactory, config)

        When calling getPosts.run() itReturns Flowable.just(Pair(Pagination(), listOf(FAKE_POST)))

        viewModel.postsLiveData.observeForever {} // only to call flow paging
//        When calling postsDataSourceFactory.postsDataSourceLiveData itReturns postsDataSourceLiveData
//        When calling postsDataSource.networkState itReturns networkStateLiveData
//        postsDataSource.networkState.value = networkState

//        postsDataSourceLiveData.value = postsDataSource
    }


    @Test
    fun `should get posts`() {
        viewModel.postsLiveData.value!! shouldContainAll listOf(Fake_PRESENTATION_POST)
    }

    @Test
    fun `should get network state and expected success`() {
        val result =  NetworkState.LOADED
//        postsDataSourceFactory.postsDataSourceLiveData.value?.networkState?.value = result
        viewModel.getNetworkState().value shouldBe result
    }

//    @Test
//    fun `should call refresh and expected called data source to invalid`() {
//        viewModel.refresh()
//
//        Verify on postsDataSource that postsDataSource.invalidate() was called
//    }
//
//    @Test
//    fun `should call network state and expected called data source to get network state`() {
//        networkStateLiveData.value = networkState
//
//        viewModel.getNetworkState().value shouldEqual networkState
//    }
}