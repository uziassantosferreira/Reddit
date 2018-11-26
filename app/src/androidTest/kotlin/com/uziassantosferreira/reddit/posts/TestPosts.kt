package com.uziassantosferreira.reddit.posts

import android.view.View
import com.agoda.kakao.*
import com.uziassantosferreira.reddit.R
import org.hamcrest.Matcher

class TestPosts : Screen<TestPosts>() {

    val loading: KView = KView { withId(R.id.progressBarLoading) }
    val error: KView = KView { withId(R.id.textViewError) }

    val recyclerView: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.textViewTitle) }
    }
}