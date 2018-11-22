package com.uziassantosferreira.reddit.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.uziassantosferreira.reddit.R

abstract class BaseFragment : Fragment() {

    val navController: NavController
        get() = Navigation.findNavController(requireActivity(), R.id.navHost)

}