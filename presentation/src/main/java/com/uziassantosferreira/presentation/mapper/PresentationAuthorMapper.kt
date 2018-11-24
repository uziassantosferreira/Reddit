package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Author
import com.uziassantosferreira.domain.model.Author as domain

object PresentationAuthorMapper: Mapper<Author, domain>() {

    override fun transformFrom(source: domain): Author = Author(name = source.name)

    override fun transformTo(source: Author): domain {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}