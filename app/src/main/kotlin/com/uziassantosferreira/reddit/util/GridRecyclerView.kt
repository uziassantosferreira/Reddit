package com.uziassantosferreira.reddit.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import android.view.animation.GridLayoutAnimationController
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * RecyclerView with support for grid animations.
 *
 * Based on:
 * https://gist.github.com/Musenkishi/8df1ab549857756098ba
 * Credit to Freddie (Musenkishi) Lust-Hed
 *
 * ...which in turn is based on the GridView implementation of attachLayoutParameters(...):
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/GridView.java
 *
 */
class GridRecyclerView : RecyclerView {

    /** @see View.View
     */
    constructor(context: Context) : super(context) {}

    /** @see View.View
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    /** @see View.View
     */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun attachLayoutAnimationParameters(
        child: View, params: ViewGroup.LayoutParams,
        index: Int, count: Int
    ) {
        val layoutManager = layoutManager
        if (adapter != null && layoutManager is StaggeredGridLayoutManager) {

            var animationParams: GridLayoutAnimationController.AnimationParameters? =
                params.layoutAnimationParameters as? GridLayoutAnimationController.AnimationParameters

            if (animationParams == null) {
                // If there are no animation parameters, create new once and attach them to
                // the LayoutParams.
                animationParams = GridLayoutAnimationController.AnimationParameters()
                params.layoutAnimationParameters = animationParams
            }

            // Next we are updating the parameters

            // Set the number of items in the RecyclerView and the index of this item
            animationParams.count = count
            animationParams.index = index

            // Calculate the number of columns and rows in the grid
            val columns = layoutManager.spanCount
            animationParams.columnsCount = columns
            animationParams.rowsCount = count / columns

            // Calculate the column/row position in the grid
            val invertedIndex = count - 1 - index
            animationParams.column = columns - 1 - invertedIndex % columns
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns

        } else {
            // Proceed as normal if using another type of LayoutManager
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }
}