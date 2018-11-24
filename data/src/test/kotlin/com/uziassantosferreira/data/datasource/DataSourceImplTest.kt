package com.uziassantosferreira.data.datasource

import com.nhaarman.mockitokotlin2.mock
import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.model.JsonGenericResponseWrapper
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

@RunWith(MockitoJUnitRunner::class)
class DataSourceImplTest {

    private val service: RedditService = mock()

    @InjectMocks
    private lateinit var dataSource: DataSourceImpl


    @Before
    fun `set up mocks`() {
        When calling service.getPostsByCommunity() itReturns Flowable.just(JsonGenericResponseWrapper())
    }

    @Test
    fun `should be call service when get posts by community and expected empty`() {
        dataSource.getPostsByCommunity()
            .test()
            .assertValue(Pair(Pagination(), emptyList()))
            .assertComplete()
            .assertNoErrors()
    }
}