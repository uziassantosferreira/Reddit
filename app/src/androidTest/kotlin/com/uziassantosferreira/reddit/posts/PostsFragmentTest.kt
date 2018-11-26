package com.uziassantosferreira.reddit.posts

import android.content.Intent
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uziassantosferreira.reddit.MainActivity
import com.uziassantosferreira.reddit.application.RedditApplicationTest
import com.uziassantosferreira.reddit.mocks.FakeRepository
import com.uziassantosferreira.reddit.mocks.FakeStatus
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.android.getKoin

@RunWith(AndroidJUnit4::class)
@LargeTest
class PostsFragmentTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, false)

    private val screen = TestPosts()

    @Test
    fun shouldBeShowList() {
        FakeRepository.fakeStatus = FakeStatus.SUCCESS
        rule.launchActivity(getIntent())
        cleanDependencies()
        screen {
            recyclerView {
                isVisible()
                lastChild<TestPosts.Item> {
                    title {
                        hasText("Test")
                    }
                }
            }
        }
    }

    @Test
    fun shouldBeShowLoading() {
        FakeRepository.fakeStatus = FakeStatus.LOADING
        rule.launchActivity(getIntent())
        cleanDependencies()
        screen {
            loading {
                isVisible()
            }
        }
    }

    @Test
    fun shouldBeShowError() {
        FakeRepository.fakeStatus = FakeStatus.FAILURE
        rule.launchActivity(getIntent())
        cleanDependencies()
        screen {
            error {
                isVisible()
            }
        }
    }

    private fun cleanDependencies() {
        (rule.activity.application as RedditApplicationTest).getKoin().close()
        (rule.activity.application as RedditApplicationTest).startDi()
    }

    private fun getIntent(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation()
            .targetContext
        return Intent(targetContext, MainActivity::class.java)
    }
}