package com.uziassantosferreira.reddit.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.load(url: String, loadOnlyFromCache: Boolean = false, onLoadingFinished: () -> Unit = {}) {
    if (url.isEmpty()){
        Glide.with(this).clear(this)
        return

    }

    val listener = object : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).onlyRetrieveFromCache(loadOnlyFromCache))
        .listener(listener)
        .into(this)
}