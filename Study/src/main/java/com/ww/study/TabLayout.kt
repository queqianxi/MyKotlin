package com.ww.study

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * @author: Ww
 * @date: 2021/12/2
 */
class TabLayout(context: Context?, attributeSet: AttributeSet?) : ViewGroup(context, attributeSet) {

    private val childrenBounds = mutableListOf<Rect>()
    
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()){
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0

        var lineWidthUsed = 0
        var lineMaxHeight = 0

        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        for ((index, child) in children.withIndex()){
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            //判断是否超出宽的边界
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.measuredWidth > specWidthSize){
                lineWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index >= childrenBounds.size){
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight)

            lineWidthUsed += child.measuredWidth
            widthUsed = max(widthUsed, lineWidthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }
        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        //measureChildWithMargins这个方法里强转成了MarginLayoutParams,可以在这里修改转为MarginLayoutParams
        return MarginLayoutParams(context, attrs)
    }
//    //view宽高模式，分三种：UNSPECIFIED AT_MOST EXACTLY 未指定、最多、精确
//    val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
//    val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
//
//    val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
//    //子view的宽mode和具体值
//    var childWidthSpecMode = 0
//    var chileWidthSpecSize = 0
//    when(layoutParams.width){
//        LayoutParams.MATCH_PARENT ->
//        when(widthSpecMode){
//            //当父view为精确值，子view match时，子view也为精确值了
//            //当父view为有最大值时，子view也是精确值
//            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
//                childWidthSpecMode = MeasureSpec.EXACTLY
//                chileWidthSpecSize = widthSpecSize - widthUsed
//            }
//            //当父view为有最大值时，子view也是精确值
//            MeasureSpec.UNSPECIFIED -> {
//                childWidthSpecMode = MeasureSpec.UNSPECIFIED
//                chileWidthSpecSize = 0
//            }
//        }
//        LayoutParams.WRAP_CONTENT ->
//        when(widthSpecMode){
//            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
//                childWidthSpecMode = MeasureSpec.AT_MOST
//                chileWidthSpecSize = widthSpecSize - widthUsed
//            }
//            MeasureSpec.UNSPECIFIED -> {
//                childWidthSpecMode = MeasureSpec.UNSPECIFIED
//                chileWidthSpecSize = 0
//            }
//        }
//        else -> {
//            childWidthSpecMode = MeasureSpec.EXACTLY
//            chileWidthSpecSize = layoutParams.width
//        }
}