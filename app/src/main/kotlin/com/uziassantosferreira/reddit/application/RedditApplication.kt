package com.uziassantosferreira.reddit.application

import android.app.Application
import com.uziassantosferreira.data.di.networkModule
import com.uziassantosferreira.reddit.BuildConfig
import com.uziassantosferreira.reddit.di.applicationModule
import com.uziassantosferreira.reddit.di.detailModule
import com.uziassantosferreira.reddit.di.postsModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger

class RedditApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(networkModule, applicationModule, postsModule, detailModule),
            logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger())
    }
}