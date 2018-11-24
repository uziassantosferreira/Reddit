package com.uziassantosferreira.reddit.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.reddit.R
import kotlinx.android.synthetic.main.list_item_post.view.*

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(post: Post?) {
        itemView.textViewTitle.text = post?.title
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_post, parent, false)
            return PostViewHolder(view)
        }
    }

}