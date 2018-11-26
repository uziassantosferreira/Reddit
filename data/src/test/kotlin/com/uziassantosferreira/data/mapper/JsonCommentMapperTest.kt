package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonComment
import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Comment
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test

class JsonCommentMapperTest {
    companion object {
        private const val FAKE_TEXT = "Teste Title"
        private const val FAKE_AUTHOR_NAME = "Teste"
        private const val FAKE_ID = "1231F"
    }

    private val post = Comment(text = FAKE_TEXT, author = Author(FAKE_AUTHOR_NAME), remoteId = FAKE_ID)

    private val jsonPost = JsonComment(body = FAKE_TEXT, author= FAKE_AUTHOR_NAME, id = FAKE_ID)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = JsonCommentMapper.transformTo(jsonPost)

        converted shouldEqual post
    }

    @Test
    fun `should be return exception when transform domain to json`() {
        val remote = { JsonCommentMapper.transformFrom(post) }

        remote shouldThrow NotImplementedError::class
    }
}