package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Image
import com.uziassantosferreira.domain.model.Post
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test
import java.util.*
import com.uziassantosferreira.presentation.model.Post as Presentation

class PresentationPostMapperTest {

    companion object {
        private const val FAKE_URL = "www.image.com.br"
        private const val FAKE_TITLE = "Teste Title"
        private const val FAKE_AUTHOR_NAME = "Teste"
        private val FAKE_PRESENTATION_IMAGE = com.uziassantosferreira.presentation.model.Image(url = FAKE_URL)
        private val FAKE_IMAGE = Image(url = FAKE_URL)
        private val FAKE_DATE = Date(1000)
    }

    private val domain = Post(title = FAKE_TITLE, date = FAKE_DATE, author = Author(FAKE_AUTHOR_NAME),
        imagePreview = listOf(FAKE_IMAGE, FAKE_IMAGE))

    private val presentation = Presentation(title = FAKE_TITLE, date = FAKE_DATE,
        author = com.uziassantosferreira.presentation.model.Author(FAKE_AUTHOR_NAME),
        imagePreview = listOf(FAKE_PRESENTATION_IMAGE, FAKE_PRESENTATION_IMAGE))

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = PresentationPostMapper.transformFrom(domain)

        converted shouldEqual presentation
    }

    @Test
    fun `should be return exception when transform presentation to domain`() {
        val remote = { PresentationPostMapper.transformTo(presentation) }

        remote shouldThrow NotImplementedError::class
    }
}