package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.presentation.model.Image as Presentation
import com.uziassantosferreira.domain.model.Image
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Assert.*
import org.junit.Test

class PresentationImageMapperTest {

    companion object {
        private const val FAKE_URL = "www.image.com.br"
        private const val FAKE_WIDTH = 100
        private const val FAKE_HEIGHT = 100
    }

    private val domain = Image(url = FAKE_URL, width = FAKE_WIDTH, height = FAKE_HEIGHT)

    private val presentation = Presentation(url = FAKE_URL, width = FAKE_WIDTH, height = FAKE_HEIGHT)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = PresentationImageMapper.transformFrom(domain)

        converted shouldEqual presentation
    }

    @Test
    fun `should be return exception when transform presentation to domain`() {
        val remote = { PresentationImageMapper.transformTo(presentation) }

        remote shouldThrow NotImplementedError::class
    }
}