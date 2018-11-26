package com.uziassantosferreira.reddit.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.presentation.exception.Failure
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.extension.getMessage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.list_item_network_state.*

abstract class BaseFragment : Fragment() {

    val navController: NavController
        get() = Navigation.findNavController(requireActivity(), R.id.navHost)


    open fun setInitialLoadingState(networkState: NetworkState?) {
        textViewError.visibility = if (networkState?.failure != null) View.VISIBLE else View.GONE
        networkState?.failure?.let {
            textViewError.text = it.getMessage(requireContext())
            if (it is Failure.EmptyList){
                buttonRetry.visibility = View.GONE
            }
        }
        buttonRetry.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        recyclerView.visibility = if (networkState?.status == Status.SUCCESS) View.VISIBLE else View.GONE
        progressBarLoading.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }
}