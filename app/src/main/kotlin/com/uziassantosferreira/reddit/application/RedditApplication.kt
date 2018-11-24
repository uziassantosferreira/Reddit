package com.uziassantosferreira.reddit.application

import android.app.Application
import com.uziassantosferreira.data.di.networkModule
import org.koin.android.ext.android.startKoin

class RedditApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(networkModule))
    }
}