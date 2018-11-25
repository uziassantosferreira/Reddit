package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.domain.model.Post as domain

object PresentationPostMapper: Mapper<Post, domain>() {

    override fun transformFrom(source: domain): Post = Post(title = source.title,
        totalComments = source.totalComments, totalLikes = source.totalLikes, date = source.date,
        text = source.text,
        author = PresentationAuthorMapper.transformFrom(source.author),
        imagePreview = PresentationImageMapper.transformFromList(source.imagePreview))

    override fun transformTo(source: Post): domain {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}