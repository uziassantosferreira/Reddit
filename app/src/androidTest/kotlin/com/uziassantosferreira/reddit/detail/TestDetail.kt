package com.uziassantosferreira.reddit.detail

import android.view.View
import com.agoda.kakao.*
import com.uziassantosferreira.reddit.R
import org.hamcrest.Matcher

class TestDetail : Screen<TestDetail>() {

    val loading: KView = KView { withId(R.id.progressBarLoading) }
    val error: KView = KView { withId(R.id.textViewError) }
    val text: KTextView = KTextView { withId(R.id.textViewText) }

    val recyclerView: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val text: KTextView = KTextView(parent) { withId(R.id.textViewText) }
    }
}