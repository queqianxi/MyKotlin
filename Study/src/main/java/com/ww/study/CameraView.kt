package com.ww.study

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave

/**
 * 范围裁切和几何变换
 * @author: Ww
 * @date: 2021/11/22
 */
class CameraView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val bitmap = getImage(300.dp.toInt())
    private val camera = Camera()

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 30f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 25f
        set(value) {
            field = value
            invalidate()
        }

    init {
        //为了提高光源的位置，从而缩小图片的大小
        camera.setLocation(0f, 0f, -10 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        cut(canvas)
        clip2(canvas)
    }

    /**实现一张图片斜着翻折，翻书效果，先旋转后裁剪*/
    fun clip2(canvas: Canvas){
        canvas.withSave {
            canvas.translate(50.dp + bitmap.width / 2, 100.dp + bitmap.height / 2)
            canvas.rotate(-flipRotation)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            //把范围扩大再裁剪
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                canvas.clipOutRect(-bitmap.width.toFloat(), 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
            }
            canvas.rotate(flipRotation)
            canvas.translate((-50).dp - bitmap.width / 2, (-100).dp - bitmap.height / 2)
            canvas.drawBitmap(bitmap, 50.dp, 100.dp, paint)
        }

        canvas.save()
        canvas.translate(50.dp + bitmap.width / 2, 100.dp + bitmap.height / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-bitmap.width.toFloat(), 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        canvas.rotate(flipRotation)
        canvas.translate((-50).dp - bitmap.width / 2, (-100).dp - bitmap.height / 2)
        canvas.drawBitmap(bitmap, 50.dp, 100.dp, paint)
        canvas.restore()
    }

    /**实现一张图片下半部分翻折，翻书效果*/
    fun clip(canvas: Canvas){
        canvas.save()
        canvas.translate(50.dp + bitmap.width / 2, 100.dp + bitmap.height / 2)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            canvas.clipOutRect(-bitmap.width / 2f, 0f, bitmap.width / 2f, bitmap.height / 2f)
        }
        canvas.translate((-50).dp - bitmap.width / 2, (-100).dp - bitmap.height / 2)
        canvas.drawBitmap(bitmap, 50.dp, 100.dp, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(50.dp + bitmap.width / 2, 100.dp + bitmap.height / 2)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-bitmap.width / 2f, 0f, bitmap.width / 2f, bitmap.height / 2f)
        canvas.translate((-50).dp - bitmap.width / 2, (-100).dp - bitmap.height / 2)
        canvas.drawBitmap(bitmap, 50.dp, 100.dp, paint)
        canvas.restore()
    }

    /**裁剪*/
    fun cut(canvas: Canvas){
        //clipRect()
        //clipPath() clipPath() 切出来的圆为什么没有抗锯⻮效果？因为「强⾏切边」
        //clipOutRect() / clipOutPath()
//        path.addCircle(200.dp, 250.dp, bitmap.height / 2f, Path.Direction.CCW)
//        canvas.clipPath(path)

        canvas.clipRect(Rect(50.dp.toInt(), 100.dp.toInt(), 200.dp.toInt(), 100.dp.toInt() + bitmap.height))
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