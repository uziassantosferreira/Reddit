package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.domain.model.Post as domain

object PresentationPostMapper: Mapper<Post, domain>() {

    override fun transformFrom(source: domain): Post = Post(title = source.title, date = source.date,
        author = PresentationAuthorMapper.transformFrom(source.author),
        imagePreview = PresentationImageMapper.transformFromList(source.imagePreview),
        text = source.text, remoteId = source.remoteId, link = source.link)

    override fun transformTo(source: Post): domain {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}