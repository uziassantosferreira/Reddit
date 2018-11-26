package com.uziassantosferreira.presentation.mapper

import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Comment
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test
import com.uziassantosferreira.presentation.model.Comment as Presentation

class PresentationCommentMapperTest {

    companion object {
        private const val FAKE_TITLE = "Teste Title"
        private const val FAKE_AUTHOR_NAME = "Teste"
        private const val FAKE_ID = "f311F"
    }

    private val domain = Comment(text = FAKE_TITLE, author = Author(FAKE_AUTHOR_NAME), remoteId = FAKE_ID)

    private val presentation = Presentation(text = FAKE_TITLE,
        author = com.uziassantosferreira.presentation.model.Author(FAKE_AUTHOR_NAME), remoteId = FAKE_ID)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = PresentationCommentMapper.transformFrom(domain)

        converted shouldEqual presentation
    }

    @Test
    fun `should be return exception when transform presentation to domain`() {
        val remote = { PresentationCommentMapper.transformTo(presentation) }

        remote shouldThrow NotImplementedError::class
    }
}