package com.uziassantosferreira.data.mapper

import com.uziassantosferreira.data.model.JsonPost
import com.uziassantosferreira.domain.model.Post
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test

class JsonPostMapperTest {
    companion object {
        private const val FAKE_TITLE = "Teste Title"
    }

    private val post = Post(title = FAKE_TITLE)

    private val jsonPost = JsonPost(title = FAKE_TITLE)

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