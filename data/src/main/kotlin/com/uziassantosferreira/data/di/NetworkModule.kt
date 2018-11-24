package com.uziassantosferreira.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uziassantosferreira.data.api.RedditService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    const val API_URL : String = "https://www.reddit.com/"
}

val networkModule = module {

    single<RxJava2CallAdapterFactory> {  RxJava2CallAdapterFactory.create() }

    single<GsonConverterFactory> { GsonConverterFactory.create(get()) }

    single<Gson> { GsonBuilder().create() }

    single<OkHttpClient> {
        OkHttpClient.Builder()
        .addInterceptor(get())
        .build()
    }


    single<Interceptor> {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
         logger
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .baseUrl(Network.API_URL)
            .build()
    }

    single<RedditService> {
        get<Retrofit>().create(RedditService::class.java)
    }

}