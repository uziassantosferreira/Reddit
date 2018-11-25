package com.uziassantosferreira.reddit.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.util.GlideApp
import kotlinx.android.synthetic.main.list_item_post.view.*
import java.text.DateFormat

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(post: Post?) {
        if (post == null) return
        itemView.textViewTitle.text = post.title
        itemView.textViewAuthor.text = post.author.name
        itemView.textViewSubtitle.text = DateFormat.getDateInstance(DateFormat.FULL).format(post.date)

        if (post.imagePreview.isNotEmpty() && post.imagePreview.last().url.isNotEmpty()){
            val image = post.imagePreview.last()
            GlideApp.with(itemView.context)
                .load(image.url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.imageViewThumb)
        }else {
            Glide.with(itemView).clear(itemView.imageViewThumb)
        }
    }


    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_post, parent, false)
            return PostViewHolder(view)
        }
    }

}