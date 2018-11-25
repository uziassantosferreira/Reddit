package com.uziassantosferreira.data.api

import com.uziassantosferreira.data.model.JsonGenericList
import com.uziassantosferreira.data.model.JsonGenericResponseWrapper
import com.uziassantosferreira.data.model.JsonPost
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {

    @GET("{community}/new/.json")
    fun getPostsByCommunity(@Path("community") input: String = "",
                            @Query("after") nextPage: String = "",
                            @Query("raw_json") rawJson: Int = 1): Flowable<JsonGenericResponseWrapper<JsonGenericList<JsonPost>>>

}