package com.uziassantosferreira.reddit.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.reddit.R
import kotlinx.android.synthetic.main.list_item_comment.view.*

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(comment: Comment?) {
        if (comment == null) return

        itemView.textViewText.text = comment.text
    }

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_comment, parent, false)
            return CommentViewHolder(view)
        }
    }

}