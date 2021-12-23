package com.ww.study

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max


/**
 * @author: Ww
 * @date: 2021/12/3
 */
class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    var childrenBounds: MutableList<Rect> = ArrayList()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineHeight = 0
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (widthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.measuredWidth > widthSize) {
                lineWidthUsed = 0
                heightUsed += lineHeight
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            var childBounds: Rect
            if (childrenBounds.size <= i) {
                childBounds = Rect()
                childrenBounds.add(childBounds)
            } else {
                childBounds = childrenBounds[i]
            }
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth,
                    heightUsed + child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            widthUsed = max(lineWidthUsed, widthUsed)
            lineHeight = max(lineHeight, child.measuredHeight)
        }
        val measuredWidth = widthUsed
        heightUsed += lineHeight
        val measuredHeight = heightUsed
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            val childBounds: Rect = childrenBounds[i]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}