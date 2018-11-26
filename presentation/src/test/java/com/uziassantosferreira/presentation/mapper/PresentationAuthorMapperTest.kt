package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.presentation.model.Author as Presentation
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test

class PresentationAuthorMapperTest {

    companion object {
        private const val FAKE_NAME = "Test"
    }

    private val domain = Author(name = FAKE_NAME)

    private val presentation = Presentation(name = FAKE_NAME)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = PresentationAuthorMapper.transformFrom(domain)

        converted shouldEqual presentation
    }

    @Test
    fun `should be return exception when transform presentation to domain`() {
        val remote = { PresentationAuthorMapper.transformTo(presentation) }

        remote shouldThrow NotImplementedError::class
    }
}