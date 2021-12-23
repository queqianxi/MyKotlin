package com.ww.study

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author: Ww
 * @date: 2021/11/10
 */

class MyTimeView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet){
    /**抗锯齿标志ANTI_ALIAS_FLAG*/
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dash = Path()
    /**给path设置样式*/
    private lateinit var pathHEffect: PathEffect
    private lateinit var pathMEffect: PathEffect
    private lateinit var pathMeasure : PathMeasure

    private var hour = 22
    private var minus = 14
    private var second = 55

    var RADIUS = 150f.px
        set(value) {
            field = value
            hourLength = RADIUS / 2
            minusLength = RADIUS / 4 * 3
            secondLength = RADIUS / 6 * 5
            DASH_WIDTH = value / 30
            DASH_LENGTH = value / 30
            invalidate()
        }
    private var DASH_WIDTH = 5f.px
    private var DASH_LENGTH = 5f.px
    private var hourLength = RADIUS / 2
    private var minusLength = RADIUS / 4 * 3
    private var secondLength = RADIUS / 6 * 5
    
    var startX = 0f
    var startY = 0f

    /**当view尺寸改变时调用，比如view里画圆，圆和view一样大，view大小改变了，圆也应该也要跟着改变*/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        //CCW逆时针，CW顺时针
//        path.addCircle(startX, startY, RADIUS, Path.Direction.CCW)
//        paint.color = 0xFFFFFFFF.toInt()

        //Path.FillType.WINDING相交的地方，同方向+1，不同-1，大于0内部；Path.FillType.EVEN_ODD覆盖层数两两抵消，
        //剩1内部，不剩外部
//        path.fillType = Path.FillType.EVEN_ODD

        //        path.reset()
        startX = width / 2f
        startY = height / 2f

        //测量刻度圆周长
        path.addCircle(startX, startY, RADIUS - 10f.px, Path.Direction.CCW)
        pathMeasure = PathMeasure(path, false)

        path.reset()
        //设置刻度样式
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
        pathHEffect = PathDashPathEffect(dash, pathMeasure.length / 12, 0f, PathDashPathEffect.Style.ROTATE)

        dash.reset()
        dash.addRect(0f, 0f, 2f.px, 2f.px, Path.Direction.CCW)
        pathMEffect = PathDashPathEffect(dash, pathMeasure.length / 60, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBg(canvas)
        drawMinusDash(canvas)
        drawHourDash(canvas)

        drawHour(canvas)
        drawMinus(canvas)
        drawSecond(canvas)
    }
    /**先画背景圆*/
    private fun drawBg(canvas: Canvas){
        path.addCircle(startX, startY, RADIUS, Path.Direction.CCW)
        paint.style = Paint.Style.FILL
        paint.color = 0xFFFFFFFF.toInt()
        canvas.drawPath(path, paint)
    }
    /**画刻度*/
    private fun drawMinusDash(canvas: Canvas){
        paint.pathEffect = pathMEffect
        paint.color = 0xFF333333.toInt()
        canvas.drawCircle(startX, startY, RADIUS - 10f.px, paint)
        paint.pathEffect = null
    }
    /**画刻度*/
    private fun drawHourDash(canvas: Canvas){
        paint.pathEffect = pathHEffect
        paint.color = 0xFF333333.toInt()
        canvas.drawCircle(startX, startY, RADIUS - 10f.px, paint)
        paint.pathEffect = null
    }
    /**画时针*/
    private fun drawHour(canvas: Canvas){
        paint.color = 0xFF333333.toInt()
        paint.strokeWidth = 4f.px
        canvas.drawLine(startX, startY,
            startX + hourLength * cos(markToRadians(hour, true).toFloat()),
            startY + hourLength * sin(markToRadians(hour, true).toFloat()), paint)
    }
    /**画分针*/
    private fun drawMinus(canvas: Canvas){
        paint.color = 0xFF333333.toInt()
        paint.strokeWidth = 2f.px
        canvas.drawLine(startX, startY,
            startX + minusLength * cos(markToRadians(minus, false).toFloat()),
            startY + minusLength * sin(markToRadians(minus, false).toFloat()), paint)
    }
    /**画秒针*/
    private fun drawSecond(canvas: Canvas){
        paint.color = Color.BLUE
        paint.strokeWidth = 1f.px
        canvas.drawLine(startX, startY,
            startX + secondLength * cos(markToRadians(second, false).toFloat()),
            startY + secondLength * sin(markToRadians(second, false).toFloat()), paint)

        paint.color = Color.GRAY
        canvas.drawCircle(startX, startY, 5f.px, paint)
    }

    private fun markToRadians(mark : Int, isHour : Boolean) =
        if (isHour) Math.toRadians((360 / 12 * (mark + minus / 60f)- 90).toDouble())
        else Math.toRadians((360 / 60 * mark - 90).toDouble())

    fun updateTime(){
        hour = Calendar.getInstance(Locale.CHINESE).get(Calendar.HOUR)
        minus = Calendar.getInstance(Locale.CHINESE).get(Calendar.MINUTE)
        second = Calendar.getInstance(Locale.CHINESE).get(Calendar.SECOND)
        invalidate()
    }
}