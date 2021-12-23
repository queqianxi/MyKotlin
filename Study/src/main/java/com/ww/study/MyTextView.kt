package com.ww.study

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * 文本有5条基线，从上到下一次为top, ascent, baseLine, descent, bottom,
 * 静态文字时候，为了让文字居中，取文字实际位置取中；
 * 但动态文字时候不能这样取，会导致文字上下跳，一般取ascent, descent, 汉字一般在这之间
 * @author: Ww
 * @date: 2021/11/18
 */
class MyTextView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20.dp
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20.dp
    }

    private val bounds = Rect()

    private val fontMetrics = Paint.FontMetrics()

    private val testText = "queqianxi"

    private val bitmap = getImage(200.dp.toInt())

    private val multiText = "一素笔，一薄笺，想将满腹相思书撰。谁知诉情的开端，竟是那么地难。或许今生今世，我都无法写成完结那篇。" +
            "一扇窗，一丝弦，孤处落寞无边。那无尽的想念，随着我的心碎，不小心滴落到了指尖，流淌在琴弦。" +
            "于是，在天宇间流传的凄婉，是我无法言说的痛楚和心酸。一芯灯，一书卷，对繁星缱绻，向天涯无言，与凄凉作伴，" +
            "拥思愁成眠。我望归的眼泪，夜夜浸湿枕畔。我好想好想在某夜，你用凝香的小手，能为我轻轻擦干。" +
            "一壶茶，一枝烟，又能熬过多少时间。真不知道还要经历多久，才能停止思念。为什么我的爱，总是被你搁浅。" +
            "还要历尽多少伤心磨难，才可以登上那相爱的船。秋风已寒，夜正阑珊，独倚栏杆，守望你曾经的携手相牵。" +
            "坐看翠竹映浅潭，清风揉皱水底天。心怎堪，破残楼外，忽闻一声归雁。"

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawMultiline(canvas)
//        drawSimpleText(canvas)
    }

    /**简单文字的绘制*/
    private fun drawSimpleText(canvas: Canvas){
        //画进度
        paint.color = 0xFFDDDDDD.toInt()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 25.dp
        canvas.drawCircle(width / 2f, height / 2f, 150.dp, paint)
        paint.color = 0xFFE27508.toInt()
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(width / 2f - 150.dp, height / 2f - 150.dp,
                width / 2f + 150.dp, height / 2f + 150.dp, -90f, 270f, false, paint)

        //画垂直居中的文字
        paint.style = Paint.Style.FILL
        paint.textSize = 60.dp
        paint.textAlign = Paint.Align.CENTER
        paint.getTextBounds(testText, 0, testText.length, bounds)
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(testText, width / 2f, height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2f, paint)

        //画左对齐文字
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText(testText, 0f, 100.dp, paint)

        paint.textSize = 30.dp
        paint.getTextBounds(testText, 0, testText.length, bounds)
        paint.getFontMetrics(fontMetrics)
        canvas.drawText(testText, -bounds.left.toFloat(), -fontMetrics.top, paint)
    }

    /**绘制多行文字*/
    private fun drawMultiline(canvas: Canvas){
        //在Android开发中，Canvas.drawText不会换行，即使一个很长的字符串也只会显示一行，超出部分会隐藏在屏幕之外。
        //StaticLayout是android中处理文字的一个工具类，StaticLayout 处理了文字换行的问题
//        val staticLayout = StaticLayout.Builder
//                .obtain(multiText, 0, multiText.length, textPaint, width)
//                .build()
//        staticLayout.draw(canvas)

        canvas.drawBitmap(bitmap, (width - bitmap.width).toFloat(), 100.dp, paint)

        paint.getFontMetrics(fontMetrics)

        val measureWidth = floatArrayOf(0f)
        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var imageWidth: Int
        while (start < multiText.length){
            //据顶部的偏移加上本身的底
            val bottom = verticalOffset + fontMetrics.bottom
            //据顶部的偏移加上本身的顶
            imageWidth = if (bottom <= 100.dp || verticalOffset + fontMetrics.top >= 100.dp + bitmap.height){
                0
            }else{
                bitmap.width
            }
            //测量文本，如果测量的宽度超过 maxWidth，则提前停止。返回测量的字符数，如果测量宽度不为空，则在其中返回测量的实际宽度。
            count = paint.breakText(multiText, start,multiText.length, true, width.toFloat() - imageWidth, measureWidth)
            canvas.drawText(multiText, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing

        }
    }

    fun getImage(width : Int) : Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.too_full, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.too_full, options)
    }
}