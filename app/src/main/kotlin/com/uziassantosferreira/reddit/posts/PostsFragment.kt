package com.uziassantosferreira.reddit.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment: BaseFragment() {

    private val postsAdapter = PostsAdapter(onClickItem())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = postsAdapter

        postsAdapter.list.addAll(listOf(Post(), Post(), Post()))


    }

    private fun onClickItem(): (Post) -> Unit = {}
}