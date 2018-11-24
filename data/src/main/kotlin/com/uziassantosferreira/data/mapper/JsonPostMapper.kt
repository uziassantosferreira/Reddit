package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonPost
import com.uziassantosferreira.domain.model.Post

object JsonPostMapper: Mapper<JsonPost, Post>() {
    override fun transformFrom(source: Post): JsonPost {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonPost): Post {
        return Post(title = source.title ?: "")
    }
}