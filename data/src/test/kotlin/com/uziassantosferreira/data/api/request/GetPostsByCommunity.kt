package com.uziassantosferreira.data.api.request

import com.google.gson.reflect.TypeToken
import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.api.helper.JsonObjectConverter
import com.uziassantosferreira.data.api.helper.MockRestApi
import com.uziassantosferreira.data.api.helper.fileFromResource
import com.uziassantosferreira.data.model.JsonGenericList
import com.uziassantosferreira.data.model.JsonGenericResponseWrapper
import com.uziassantosferreira.data.model.JsonPost
import org.junit.Test
import org.koin.standalone.inject

class GetPostsByCommunity: MockRestApi() {
    override val resource: String = "GetPostsByCommunity.json"

    private lateinit var response: JsonGenericResponseWrapper<JsonGenericList<JsonPost>>

    private val service by inject<RedditService>()

    override fun setUp() {
        super.setUp()

        val readFileResult = fileFromResource(resource, javaClass)
        val type = object : TypeToken<JsonGenericResponseWrapper<JsonGenericList<JsonPost>>>() {}.type
        response = JsonObjectConverter.convertFromJson(readFileResult, type)
    }

    @Test
    fun getPostsByCommunityAndExpectedSuccess() {
        service.getPostsByCommunity()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(response)
    }
}