package com.uziassantosferreira.reddit.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uziassantosferreira.reddit.R
import kotlinx.android.synthetic.main.list_item_header.view.*

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(text: Int) {
        itemView.textViewTitle.setText(text)
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_header, parent, false)
            return HeaderViewHolder(view)
        }
    }

}