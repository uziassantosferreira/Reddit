package com.uziassantosferreira.reddit

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.uziassantosferreira.data.api.RedditService
import com.uziassantosferreira.data.datasource.DataSourceImpl
import com.uziassantosferreira.data.repository.RepositoryImpl
import com.uziassantosferreira.reddit.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val service: RedditService by inject()
    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RepositoryImpl(DataSourceImpl(service))
            .getPostsByCommunity("r/Android")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.navHost)
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp() =
         navController.navigateUp()
}
