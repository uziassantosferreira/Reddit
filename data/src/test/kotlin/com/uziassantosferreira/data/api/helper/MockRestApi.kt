package com.uziassantosferreira.data.api.helper

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.uziassantosferreira.data.di.Network.API_URL
import com.uziassantosferreira.data.di.Network.API_URL_NAME
import com.uziassantosferreira.data.di.networkModule
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.koin.test.declare
import org.koin.test.declareMock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class MockRestApi: KoinTest {
    private lateinit var server: MockWebServer

    private lateinit var file: String
    lateinit var endpoint: String
    abstract val resource: String

    @Before
    open fun setUp() {
        setupReadFile()
        setupServer()
        startKoin(listOf(networkModule))
        declare {single(name = API_URL_NAME, override = true) { endpoint } }
    }

    private fun setupReadFile() {
        file = fileFromResource(resource, javaClass)
    }

    private fun setupServer() {
        server = MockWebServer()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(file)
        )
        server.start()
        endpoint = server.url("/").toString()
    }

    @After
    fun tearDown() {
        server.shutdown()
        stopKoin()
    }

}

