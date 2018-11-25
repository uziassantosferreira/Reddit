package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonImage
import com.uziassantosferreira.domain.model.Image

object JsonImageMapper: Mapper<JsonImage, Image>() {

    override fun transformFrom(source: Image): JsonImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformTo(source: JsonImage): Image = Image(url = source.url ?: "", width = source.width ?: 0,
        height = source.width ?: 0)

}