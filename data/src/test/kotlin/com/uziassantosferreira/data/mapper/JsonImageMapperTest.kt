package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonImage
import com.uziassantosferreira.domain.model.Image
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test

class JsonImageMapperTest {
    companion object {
        private const val FAKE_URL = "www.image.com.br"
        private const val FAKE_WIDTH = 100
        private const val FAKE_HEIGHT = 100
    }

    private val image = Image(url = FAKE_URL, width = FAKE_WIDTH, height = FAKE_HEIGHT)

    private val jsonImage = JsonImage(url = FAKE_URL, width = FAKE_WIDTH, height = FAKE_HEIGHT)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = JsonImageMapper.transformTo(jsonImage)

        converted shouldEqual image
    }

    @Test
    fun `should be return exception when transform domain to json`() {
        val remote = { JsonImageMapper.transformFrom(image) }

        remote shouldThrow NotImplementedError::class
    }
}