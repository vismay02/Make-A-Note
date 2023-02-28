package com.vismay.makeanote.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/*Inspired from https://github.com/raxhaxor/Android-Drawable-Maker */
open class DrawableGenerator {
    var cornerRadii: CornerRadii? = null

    @ColorRes
    var solidColor: Int = 0

    @ColorRes
    var strokeColor: Int = 0
    var strokeWidth: Int = 0

    private fun generate(context: Context): ShapeDrawable {
        if (cornerRadii != null) {
            val rectShape =
                RoundRectShape(
                    floatArrayOf(
                        cornerRadii!!.topLeft.toFloat(),
                        cornerRadii!!.topLeft.toFloat(),
                        cornerRadii!!.topRight.toFloat(),
                        cornerRadii!!.topRight.toFloat(),
                        cornerRadii!!.bottomRight.toFloat(),
                        cornerRadii!!.bottomRight.toFloat(),
                        cornerRadii!!.bottomLeft.toFloat(),
                        cornerRadii!!.bottomLeft.toFloat()
                    ), null, null
                )
            val shapeDrawable = ShapeDrawable(rectShape)
            shapeDrawable.paint.color = ContextCompat.getColor(context, solidColor)
            shapeDrawable.paint.style = Paint.Style.FILL
            shapeDrawable.paint.isAntiAlias = true
            shapeDrawable.paint.flags = Paint.ANTI_ALIAS_FLAG
           // shapeDrawable.setTint(ContextCompat.getColor(context, strokeColor))
            shapeDrawable.paint.strokeWidth = strokeWidth.toFloat()
            return shapeDrawable
        }
        throw Exception("CornerRadii cannot be null!")
    }

    companion object {
        fun View.setDrawable(context: Context, drawableParams: DrawableGenerator.() -> Unit) =
            apply {
                background = DrawableGenerator().apply(drawableParams).generate(context)
            }
    }

    data class CornerRadii(
        var topLeft: Int = 0,
        var topRight: Int = 0,
        var bottomRight: Int = 0,
        var bottomLeft: Int = 0,
    )
}