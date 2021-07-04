package com.cliff.myscore.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.cliff.myscore.R
import com.cliff.myscore.bl.px


class HalfFieldDrawable(context: Context) : Drawable() {

    private val grassColor1 =
        ResourcesCompat.getColor(context.resources, R.color.grass1, context.theme)
    private val grassColor2 =
        ResourcesCompat.getColor(context.resources, R.color.grass2, context.theme)

    private val grassPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3.toFloat()
        color = ResourcesCompat.getColor(context.resources, R.color.fieldLine, context.theme)
    }

    private val rect = Rect()
    private val padding = 8.px
    private val halfInnerGoalWidth = 16.px
    private val halfInnerGoalHeight = 12.px
    private val halfGoalWidth = 52.px
    private val halfGoalHeight = 32.px
    private val cornerSize = 12.px
    private val middleCircleSize = 24.px
    private val middleInnerCircleSize = 4.px

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()
        val rowHeight = height / NUM_OF_GRASS_ROWS

        canvas.apply {
            for (i in 0..NUM_OF_GRASS_ROWS) {
                val top = i * rowHeight
                rect.set(0, top, width, top + rowHeight)
                grassPaint.color = if (i % 2 == 0) grassColor1 else grassColor2
                drawRect(rect, grassPaint)
            }

            linePaint.style = Paint.Style.STROKE
            // draw lines
            rect.set(padding, padding, width - padding, height - padding)
            drawRect(rect, linePaint)

            // draw goal
            rect.set(
                (width / 2) - halfInnerGoalWidth,
                padding,
                width / 2 + halfInnerGoalWidth,
                padding + halfInnerGoalHeight
            )

            drawRect(rect, linePaint)

            rect.set(
                (width / 2) - halfGoalWidth,
                padding,
                width / 2 + halfGoalWidth,
                padding + halfGoalHeight
            )
            drawRect(rect, linePaint)

            // draw corner arcs
            drawArc(
                padding.toFloat() - cornerSize,
                padding.toFloat() - cornerSize,
                (padding + cornerSize).toFloat(),
                (padding + cornerSize).toFloat(),
                0f,
                90f,
                false,
                linePaint
            )
            drawArc(
                (width - padding - cornerSize).toFloat(),
                padding.toFloat() - cornerSize,
                (width - padding + cornerSize).toFloat(),
                (padding + cornerSize).toFloat(),
                90f,
                90f,
                false,
                linePaint
            )
            // draw center circle
            drawArc(
                (width / 2 - middleCircleSize).toFloat(),
                (height - padding - middleCircleSize).toFloat(),
                (width / 2 + middleCircleSize).toFloat(),
                (height - padding + middleCircleSize).toFloat(),
                180f,
                180f,
                false,
                linePaint
            )
            linePaint.style = Paint.Style.FILL

            drawArc(
                (width / 2 - middleInnerCircleSize).toFloat(),
                (height - padding - middleInnerCircleSize).toFloat(),
                (width / 2 + middleInnerCircleSize).toFloat(),
                (height - padding + middleInnerCircleSize).toFloat(),
                180f,
                180f,
                false,
                linePaint
            )

        }
    }

    override fun setAlpha(alpha: Int) {
        // no-op
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // no-op
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    companion object {
        const val NUM_OF_GRASS_ROWS : Int = 8
    }

}

