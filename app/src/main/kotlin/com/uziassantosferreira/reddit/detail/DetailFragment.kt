package com.uziassantosferreira.reddit.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.base.BaseFragment
import com.uziassantosferreira.reddit.util.GlideApp
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.list_item_post.view.*
import java.text.DateFormat

class DetailFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val post = DetailFragmentArgs.fromBundle(arguments).post

        collapsingToolbarLayout.title = post.title
        collapsingToolbarLayout.subtitle = DateFormat.getDateInstance(DateFormat.FULL).format(post.date)

        textViewText.text = post.text

        if (post.imagePreview.isNotEmpty() && post.imagePreview.last().url.isNotEmpty()){
            val image = post.imagePreview.last()
            GlideApp.with(this)
                .load(image.url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewThumb)
        }else {
            Glide.with(this).clear(imageViewThumb)
        }
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }
}