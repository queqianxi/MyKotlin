package com.ww.study

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

/**
 * @author: Ww
 * @date: 2021/11/10
 */
val Float.px get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.dp get() = this.toFloat().px

fun getImage(width: Int, resources: Resources, src : Int = R.mipmap.too_full) : Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, src, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(resources, R.mipmap.too_full, options)
}