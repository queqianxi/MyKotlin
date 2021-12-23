package com.ww.study

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

/**
 * @author: Ww
 * @date: 2021/12/3
 */
class ColoredTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), CORNER_RADIUS, CORNER_RADIUS, paint)
        super.onDraw(canvas)
    }

    companion object {
        private val COLORS = intArrayOf(
                Color.parseColor("#E91E63"),
                Color.parseColor("#673AB7"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#009688"),
                Color.parseColor("#FF9800"),
                Color.parseColor("#FF5722"),
                Color.parseColor("#795548")
        )
        private val TEXT_SIZES = intArrayOf(
                16, 22, 28
        )
        private val random: Random = Random()
        private val CORNER_RADIUS = 4.dp
        private val X_PADDING = 16.dp.toInt()
        private val Y_PADDING = 8.dp.toInt()
    }

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[random.nextInt(3)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
    }
}