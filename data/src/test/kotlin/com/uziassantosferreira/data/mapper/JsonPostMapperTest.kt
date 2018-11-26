package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonContentImage
import com.uziassantosferreira.data.model.JsonImage
import com.uziassantosferreira.data.model.JsonPost
import com.uziassantosferreira.data.model.JsonPreview
import com.uziassantosferreira.domain.model.Author
import com.uziassantosferreira.domain.model.Image
import com.uziassantosferreira.domain.model.Post
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test
import java.util.*

class JsonPostMapperTest {
    companion object {
        private const val FAKE_URL = "www.image.com.br"
        private const val FAKE_TITLE = "Teste Title"
        private const val FAKE_AUTHOR_NAME = "Teste"
        private const val FAKE_ID = "1231F"
        private val FAKE_JSON_IMAGE = JsonImage(url = FAKE_URL)
        private val FAKE_IMAGE = Image(url = FAKE_URL)
        private val FAKE_DATE = Date(1000)
    }

    private val post = Post(title = FAKE_TITLE, date = FAKE_DATE, author = Author(FAKE_AUTHOR_NAME), imagePreview = listOf(
        FAKE_IMAGE, FAKE_IMAGE), remoteId = FAKE_ID)

    private val jsonPost = JsonPost(title = FAKE_TITLE,
        createdUtc = FAKE_DATE.time / 1000, authorName = FAKE_AUTHOR_NAME,
        preview = JsonPreview(listOf(JsonContentImage(image = FAKE_JSON_IMAGE, resolutions = listOf(FAKE_JSON_IMAGE)))),
        id = FAKE_ID)

    @Test
    fun `should be correctly transform and expected domain`() {
        val converted = JsonPostMapper.transformTo(jsonPost)

        converted shouldEqual post
    }

    @Test
    fun `should be return exception when transform domain to json`() {
        val remote = { JsonPostMapper.transformFrom(post) }

        remote shouldThrow NotImplementedError::class
    }
}