package com.ww.study

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

/**
 * @author: Ww
 * @date: 2021/12/2
 */
val provinceList = arrayListOf("北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "辽宁省", "吉林省",
                                "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省",
                                "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省",
                                "甘肃省", "青海省", "台湾省", "内蒙古自治区", "广西壮族自治区", "西藏自治区", "宁夏回族自治区", "新疆维吾尔自治区", "香港特别行政区", "澳门特别行政区")
private val textSizes = intArrayOf(16, 22, 28)
val colorList = arrayListOf(0xFFB40404.toInt(), 0xFF0404B4.toInt(), 0xFF088A08.toInt(), 0xFFF781F3.toInt(), 0xFFDBA901.toInt(), 0xFFB45F04.toInt())
class ColorTextView(context: Context?, attributeSet: AttributeSet?) : AppCompatTextView(context, attributeSet){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var randoms = Random()

    init {
        setTextColor(0xFFFFFFFF.toInt())
        textSize = textSizes[randoms.nextInt(3)].toFloat()
        paint.color = colorList[randoms.nextInt(colorList.size)]
        setPadding(16.dp.toInt(), 8.dp.toInt(), 16.dp.toInt(), 8.dp.toInt())

        text = provinceList[randoms.nextInt(provinceList.size)]
    }
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 5.dp, 5.dp, paint)
        super.onDraw(canvas)
    }
}