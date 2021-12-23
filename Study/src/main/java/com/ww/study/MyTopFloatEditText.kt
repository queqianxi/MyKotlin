package com.ww.study

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author: Ww
 * @date: 2021/11/26
 */
class MyTopFloatEditText(context: Context, attributeSet: AttributeSet?) : AppCompatEditText(context, attributeSet){

    private var floatTextSize = 12.dp
    private var floatTextSpace = 8.dp
    private var floatLabelShown = false

    var floatLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatLabelFraction", 1f, 0f)
    }

    init {
        setPadding(paddingLeft, (paddingTop + floatTextSize + floatTextSpace).toInt(), paddingRight, paddingBottom)

        //typeArray本质是一个整型数组，所以第一行可以用第二行代替；可以用数组下标来遍历数组里的元素
//        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar)
//        typeArray = context.obtainStyledAttributes(attributeSet, intArrayOf(R.attr.actionBarDivider))
//        typeArray.getInt(R.attr.colorOnBackground, 0)
//        typeArray.getBoolean(0, true)
    }

    private val floatPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        //如果已经显示并且文字为空则隐藏
        if (floatLabelShown && text.isNullOrEmpty()){
            floatLabelShown = false
            animator.start()
        }else if (!floatLabelShown  && !text.isNullOrEmpty()){
            //如果没有显示并且文字不为空则显示
            floatLabelShown = true
            animator.reverse()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        floatPaint.textSize = floatTextSize
        print("此时大小为$floatLabelFraction")
        floatPaint.alpha = (floatLabelFraction * 0xff).toInt()

        val currentVerticalValue = 23.dp + (1 - floatLabelFraction) * 16.dp
        canvas.drawText(hint.toString(), 5.dp, currentVerticalValue, floatPaint)
    }
}