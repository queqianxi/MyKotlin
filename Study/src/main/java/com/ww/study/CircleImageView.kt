package com.ww.study

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 为什么要 Xfermode？为了把多次绘制进⾏「合成」，例如蒙版效果：⽤ A 的形状和 B 的图案;x英文中trans
 * @author: Ww
 * @date: 2021/11/11
 */
private val mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
class CircleImageView(context: Context?, attributeSet: AttributeSet?) : View(context,attributeSet){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Canvs.saveLayer() 把绘制区域拉到单独的离屏缓冲⾥
        //绘制 A 图形
        //⽤ Paint.setXfermode() 设置 Xfermode
        //绘制 B 图形
        //⽤ Paint.setXfermode(null) 恢复 Xfermode
        //⽤ Canvas.restoreToCount() 把离屏缓冲中的合成后的图形放回绘制区域
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        //这样的绘制只对重合部分起作用
        val bitmap = getImage(width)

        val count = canvas.saveLayer(0f, 0f, width.toFloat(), bitmap.height.toFloat(), paint)
        canvas.drawOval(0f, 0f, width.toFloat(), bitmap.height.toFloat(), paint)
        paint.xfermode = mode
        canvas.drawBitmap(getImage(width), 0f, 0f, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

    fun getImage(width : Int) : Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.too_full, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.too_full, options)
    }
}