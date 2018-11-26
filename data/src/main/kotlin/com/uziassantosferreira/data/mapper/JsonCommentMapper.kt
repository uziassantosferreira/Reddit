package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonComment
import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Comment

object JsonCommentMapper: Mapper<JsonComment, Comment>() {

    override fun transformTo(source: JsonComment): Comment  = Comment(text = source.body ?: "", remoteId = source.id ?: "",
        author = Author(source.author ?: ""))

    override fun transformFrom(source: Comment): JsonComment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}