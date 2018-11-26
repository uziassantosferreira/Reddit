package com.uziassantosferreira.reddit.detail

import android.content.Intent
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.uziassantosferreira.presentation.model.Post
import com.uziassantosferreira.reddit.MainActivity
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.mocks.FakeRepository
import com.uziassantosferreira.reddit.mocks.FakeStatus
import com.uziassantosferreira.reddit.posts.PostsFragmentDirections
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailFragmentTest {

    private val post = Post(title = "Test", text = "Test")

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, false)

    private val screen = TestDetail()

    @Test
    fun shouldBeShowListComment() {
        FakeRepository.fakeStatus = FakeStatus.SUCCESS
        rule.launchActivity(getIntent())
        navigateToDetail(rule.activity)
        screen {
            recyclerView {
                isVisible()
                lastChild<TestDetail.Item> {
                    text {
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
        navigateToDetail(rule.activity)
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
        navigateToDetail(rule.activity)
        screen {
            error {
                isVisible()
            }
        }
    }

    @Test
    fun shouldBeShowText() {
        FakeRepository.fakeStatus = FakeStatus.LOADING
        rule.launchActivity(getIntent())
        navigateToDetail(rule.activity)
        screen {

            text {
                isVisible()
                hasText("Test")
            }

        }
    }

    private fun navigateToDetail(activity: MainActivity){
        activity.runOnUiThread {
            val nav = Navigation.findNavController(activity, R.id.navHost)
            val action = PostsFragmentDirections.ActionPostsFragmentToDetailFragment(post)
            nav.navigate(action)
        }
    }
    private fun getIntent(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation()
            .targetContext
        return Intent(targetContext, MainActivity::class.java)
    }
}