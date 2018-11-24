package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Image
import com.uziassantosferreira.domain.model.Image as domain

object PresentationImageMapper: Mapper<Image, domain>() {

    override fun transformFrom(source: domain): Image = Image(url = source.url,
        width = source.width, height = source.height)

    override fun transformTo(source: Image): domain {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}