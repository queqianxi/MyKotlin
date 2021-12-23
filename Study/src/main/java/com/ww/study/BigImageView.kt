package com.ww.study

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

/**
 * @author: Ww
 * @date: 2021/12/14
 */
class BigImageView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet),
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f
    private var largeScale = 0f
    private var ivWidth = 0
    private var ivHeight = 0
    /**0small, 1big, 2large*/
    private var scaleType = 0

    /**x为横向能滑动最大值，y为纵向*/
    private var canScrollX = 0f
    private var canScrollY = 0f
    /**滑动的值*/
    private var tranX = 0f
    private var tranY = 0f
    /**动画执行的百分比*/
    private var fraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    /**滑动计算器*/
    private val scroller = OverScroller(context)

    private val bitmap = getImage(300.dp.toInt(), resources)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val gestureDetector = GestureDetectorCompat(context, this)
    private val animator : ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "fraction", 0f, 1f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ivWidth = bitmap.width
        ivHeight = bitmap.height

        offsetX = (width - ivWidth) / 2f
        offsetY = (height - ivHeight) / 2f
        if(width / height.toFloat() > ivWidth / ivHeight.toFloat()){
            smallScale = height / ivHeight.toFloat()
            bigScale = width / ivWidth.toFloat()
        }else{
            bigScale = height / ivHeight.toFloat()
            smallScale = width / ivWidth.toFloat()
        }
        largeScale = bigScale * 1.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(tranX, tranY)

        var big = 0f
        var small = 0f
        when(scaleType){
            1 -> {
                big = bigScale
                small = smallScale
            }
            2 -> {
                big = largeScale
                small = bigScale
            }
            0 -> {
                big = largeScale
                small = smallScale
            }
        }
        val scale = small + (big - small) * fraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        when(scaleType){
            0 -> {
                tranX = 0f
                tranY = 0f
            }
            1 -> {
                //-=是因为此值是上个点减去当前
                tranX -= distanceX
                tranX = min(tranX, canScrollX)
                tranX = max(tranX, -canScrollX)
                tranY -= distanceY
                tranY = min(tranY, canScrollY)
                tranY = max(tranY, -canScrollY)
                invalidate()
            }
            2 -> {
                tranX -= distanceX
                tranX = min(tranX, canScrollX)
                tranX = max(tranX, -canScrollX)
                tranY -= distanceY
                tranY = min(tranY, canScrollY)
                tranY = max(tranY, -canScrollY)
                invalidate()
            }
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (scaleType != 0){
            //int startX, int startY, int velocityX, int velocityY,
            //            int minX, int maxX, int minY, int maxY
            //起始坐标即手指抬起的位置为当前位移的距离
            scroller.fling(tranX.toInt(), tranY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -canScrollX.toInt(), canScrollX.toInt(), -canScrollY.toInt(), canScrollY.toInt())
            ViewCompat.postOnAnimation(this, this)
        }
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        when(scaleType){
            0 -> {
                scaleType = 1
                canScrollX = (ivWidth * bigScale - width) / 2
                canScrollY = (ivHeight * bigScale - height) / 2
                animator.start()
            }
            1 -> {
                scaleType = 2
                canScrollX = (ivWidth * largeScale - width) / 2
                canScrollY = (ivHeight * largeScale - height) / 2
                animator.start()
            }
            2 -> {
                scaleType = 0
                canScrollX = 0f
                canScrollY = 0f
                animator.reverse()
            }
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun run() {
        //Call this when you want to know the new location. If it returns true, the
        //animation is not yet finished.
        if (scroller.computeScrollOffset()){
            tranX = scroller.currX.toFloat()
            tranY = scroller.currY.toFloat()
            invalidate()
            ViewCompat.postOnAnimation(this, this)
        }
    }
}
