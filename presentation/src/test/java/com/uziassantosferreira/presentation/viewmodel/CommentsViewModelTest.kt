package com.uziassantosferreira.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.repository.CommentsRepository
import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.presentation.model.Post
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CommentsViewModelTest {

    private val repository: CommentsRepository = mock()

    private val commentsLiveData: LiveData<PagedList<Comment>> = MutableLiveData()
    private val networkStateLiveData: LiveData<NetworkState> = MutableLiveData()

    @InjectMocks
    private lateinit var viewModel: CommentsViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun `set up mocks`() {
        When calling repository.getList("") itReturns commentsLiveData
        When calling repository.getNetworkState() itReturns networkStateLiveData
    }

    @Test
    fun `should get posts and expected call repository`() {
        val result = viewModel.getComments(Post())

        result shouldBe commentsLiveData
        Verify on repository that repository.getList("") was called
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
}