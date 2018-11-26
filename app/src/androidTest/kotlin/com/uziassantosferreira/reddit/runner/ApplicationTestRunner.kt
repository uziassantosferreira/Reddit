package com.uziassantosferreira.reddit.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.uziassantosferreira.reddit.application.RedditApplicationTest

class ApplicationTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, RedditApplicationTest::class.java.name, context)
    }
}