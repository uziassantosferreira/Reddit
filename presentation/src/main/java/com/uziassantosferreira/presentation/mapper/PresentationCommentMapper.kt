package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Comment
import com.uziassantosferreira.domain.model.Comment as Domain

object PresentationCommentMapper: Mapper<Comment, Domain>() {

    override fun transformFrom(source: Domain): Comment = Comment(text = source.text, remoteId = source.remoteId,
        author = PresentationAuthorMapper.transformFrom(source.author))

    override fun transformTo(source: Comment): Domain {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}