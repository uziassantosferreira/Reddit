package com.uziassantosferreira.reddit

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.uziassantosferreira.reddit.base.BaseActivity

class MainActivity : BaseActivity() {

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.navHost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.navHost)
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp() =
         navController.navigateUp()
}
