package com.example.myphotoapp.touchHelper

import android.R.attr.divider
import android.content.res.TypedArray
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView


class decoItem : RecyclerView.ItemDecoration() {
    var swipeController: SwipeController? = null

    fun decoItem(swipe: SwipeController) {
        this.swipeController = swipe
    }
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        swipeController!!.onDraw(c)
    }
}