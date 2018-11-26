package com.uziassantosferreira.reddit

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.uziassantosferreira.reddit.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : BaseActivity() {

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.navHost)
        setupWithNavController(toolbar, navController)

        navController.addOnNavigatedListener { _, destination ->
            if (destination.id == R.id.detailFragment){
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()

            }

        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}
