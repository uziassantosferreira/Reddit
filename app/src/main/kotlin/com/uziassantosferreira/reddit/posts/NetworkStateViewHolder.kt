package com.uziassantosferreira.reddit.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uziassantosferreira.presentation.data.datasource.NetworkState
import com.uziassantosferreira.presentation.data.datasource.Status
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.extension.getMessage
import kotlinx.android.synthetic.main.list_item_network_state.view.*

class NetworkStateViewHolder(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        //error message
        itemView.errorMessageTextView.visibility = if (networkState?.failure != null) View.VISIBLE else View.GONE
        networkState?.failure?.let {
            itemView.errorMessageTextView.text = it.getMessage(itemView.context)
        }

        //loading and retry
        itemView.retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }

}