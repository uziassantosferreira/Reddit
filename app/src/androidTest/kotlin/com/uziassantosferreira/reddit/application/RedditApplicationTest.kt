package com.uziassantosferreira.reddit.application

import android.app.Application
import com.uziassantosferreira.domain.repository.Repository
import com.uziassantosferreira.reddit.di.detailModule
import com.uziassantosferreira.reddit.di.postsModule
import com.uziassantosferreira.reddit.mocks.FakeRepository
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module

class RedditApplicationTest: Application() {

    override fun onCreate() {
        super.onCreate()

        startDi()
    }

    private fun startDi(){
        startKoin(this, listOf(applicationModule, postsModule, detailModule))
    }
}

val applicationModule = module {

    single <Repository> { FakeRepository() }

}