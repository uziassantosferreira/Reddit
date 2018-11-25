package com.uziassantosferreira.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.repository.PostsRepository
import com.uziassantosferreira.presentation.model.Post
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    private val repository: PostsRepository = mock()

    private val postsLiveData: LiveData<PagedList<Post>> = MutableLiveData()
    private val networkStateLiveData: LiveData<NetworkState> = MutableLiveData()

    @InjectMocks
    private lateinit var viewModel: PostsViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun `set up mocks`() {
        When calling repository.getPosts() itReturns postsLiveData
        When calling repository.getNetworkState() itReturns networkStateLiveData
    }

    @Test
    fun `should get posts and expected call repository`() {
        val result = viewModel.getPosts()

        result shouldBe postsLiveData
        Verify on repository that repository.getPosts() was called
    }

    @Test
    fun `should get network state and expected call repository`() {
        val result = viewModel.getNetworkState()

        result shouldBe networkStateLiveData
        Verify on repository that repository.getNetworkState() was called
    }

    @Test
    fun `should call retry and expected call repository`() {
        viewModel.retry()

        Verify on repository that repository.retry() was called
    }

    @Test
    fun `should call refresh and expected call repository`() {
        viewModel.refresh()

        Verify on repository that repository.refresh() was called
    }
}