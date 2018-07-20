package com.example.karan.myapplication2.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDivider(private val mDivider: Drawable?, private val mOffsetPxTop: Int, private val mOffsetPxBottom: Int) : RecyclerView.ItemDecoration() {
    val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        //paint.setARGB(240, 240, 240, 240)
        val leftWithMargin = 150
        val right = parent.width
        var left = parent.left
        var side = parent.right
        val mBounds = Rect()

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(child)
            left = if (adapterPosition == childCount) 0 else leftWithMargin
            side = if (adapterPosition == childCount) 0 else leftWithMargin
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child))
            val top = bottom - mDivider?.intrinsicHeight!!
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
        }
        canvas.restore()
    }

    /**
     * Determines the size and location of the offset to be added to the start
     * of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) < 1) {
            val orientation = (parent.layoutManager as LinearLayoutManager).orientation
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mOffsetPxTop
                outRect.right = mOffsetPxBottom
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mOffsetPxTop
                outRect.bottom = mOffsetPxBottom
            }
        }
    }
}