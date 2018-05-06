package com.jati.dev.movielist.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.jati.dev.movielist.R

/**
 * Created by jati on 06/05/18
 */

class ItemDivider(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(context, R.drawable.divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        divider?.let {
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + it.intrinsicHeight

                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}