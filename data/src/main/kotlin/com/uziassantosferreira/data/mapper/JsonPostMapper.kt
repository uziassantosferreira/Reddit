package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonPost
import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Image
import com.uziassantosferreira.domain.model.Post
import java.util.*

object JsonPostMapper: Mapper<JsonPost, Post>() {
    override fun transformFrom(source: Post): JsonPost {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonPost): Post {
        val imagePreview = mutableListOf<Image>()
        source.preview?.images?.forEach { content ->
            content.image?.let { image ->
                if (image.url.isNullOrEmpty()) return@let
                imagePreview.add(JsonImageMapper.transformTo(image))
            }
            content.resolutions?.let {images ->
                imagePreview.addAll(JsonImageMapper.transformToList(images))
            }
        }
        val date = if (source.createdUtc == null) Date() else Date(source.createdUtc * 1000 )
        return Post(title = source.title ?: "", imagePreview = imagePreview,
            author = Author(name = source.authorName ?: ""), date = date, text = source.text ?: "")
    }
}