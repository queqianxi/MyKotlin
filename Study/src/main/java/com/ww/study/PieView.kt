package com.ww.study

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author: Ww
 * @date: 2021/11/11
 */
data class PieData(val num : Float, val color : Int)

private var RADIUS = 150f.px

class PieView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var pieDataList : ArrayList<PieData>? = null

    private var totalNum = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (pieDataList == null){
            return
        }
        paint.style = Paint.Style.FILL
        var start = 0f
        for (i in 0 until pieDataList!!.size){
            val pieData = pieDataList!![i]
            paint.color = pieData.color
            val end = pieData.num / totalNum * 360
            //位移
            if (i == 0){
                canvas.save()
                canvas.translate(50f.px * cos(markToRadians(end, start)).toFloat(),
                        50f.px * sin(markToRadians(end, start)).toFloat())
            }
            //前几个参数为圆弧所在矩形坐标，后两个参数为起始弧度与终止弧度
            canvas.drawArc(width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS, height / 2f + RADIUS,
            start, end, true, paint)
            start += end
            //恢复
            if (i == 0){
                canvas.restore()
            }
        }
    }

    private fun markToRadians(mark : Float, start : Float) =
            Math.toRadians((mark / 2f).toDouble() + start)

    fun setPieList(pieDataList : ArrayList<PieData>){
        this.pieDataList = pieDataList
        for (pieData in pieDataList){
            totalNum += pieData.num
        }
        invalidate()
    }
}