package com.uziassantosferreira.reddit.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uziassantosferreira.reddit.R
import kotlinx.android.synthetic.main.list_item_post.view.*

class PostsAdapter(private val onClick: (Post) -> Unit) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    val list = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = list[position]
        holder.itemView.textViewTitle.text = post.title

        holder.itemView.cardView.setOnClickListener { onClick.invoke(post) }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}