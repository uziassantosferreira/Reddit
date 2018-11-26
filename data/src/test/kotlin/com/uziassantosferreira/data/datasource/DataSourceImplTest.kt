package com.uziassantosferreira.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.model.JsonGenericResponseWrapper
import com.uziassantosferreira.domain.exception.DomainThrowable
import com.uziassantosferreira.domain.exception.Failure
import com.uziassantosferreira.domain.model.Pagination
import io.reactivex.Flowable
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class DataSourceImplTest {

    private val service: RedditService = mock()

    @InjectMocks
    private lateinit var dataSource: DataSourceImpl


    @Before
    fun `set up mocks`() {
        When calling service.getPostsByCommunity() itReturns Flowable.just(JsonGenericResponseWrapper())
        When calling service.getCommentsByCommunityAndId() itReturns Flowable.just(listOf())
    }

    @Test
    fun `should be call service when get posts by community and expected empty`() {
        dataSource.getPostsByCommunity()
            .test()
            .assertValue(Pair(Pagination(), emptyList()))
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `should be call service when get posts by community and expected error no internet`() {
        When calling service.getPostsByCommunity() itReturns Flowable.error(UnknownHostException())
        dataSource.getPostsByCommunity()
            .test()
            .assertError(DomainThrowable(Failure.NoInternet))
            .assertNoValues()
            .assertNotComplete()
    }

    @Test
    fun `should be call service when get comments by community and id and expected empty`() {
        dataSource.getCommentsByCommunityAndId()
            .test()
            .assertValue(Pair(Pagination(), emptyList()))
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `should be call service when get comments by community and id and expected error no internet`() {
        When calling service.getCommentsByCommunityAndId() itReturns Flowable.error(UnknownHostException())
        dataSource.getCommentsByCommunityAndId()
            .test()
            .assertError(DomainThrowable(Failure.NoInternet))
            .assertNoValues()
            .assertNotComplete()
    }
}