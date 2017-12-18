package com.luren.arrowtox.view

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * Created by Administrator 可爱的路人 on 2017/12/18.
 * Email:513421345@qq.com
 * TODO
 */
class LineAnimViewKt : View, ValueAnimator.AnimatorUpdateListener {
    private var lines: MutableList<Line>? = null
    private var valueAnimator: ValueAnimator? = null
    private var interpolator: TimeInterpolator? = null
    private var mPaint: Paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas) {
        val measuredWidth = measuredWidth - paddingLeft - paddingRight
        val measuredHeight = measuredHeight - paddingTop - paddingBottom
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        lines?.let {
            for (l in it) {
                mPaint.color = l.color
                mPaint.style = Paint.Style.FILL
                mPaint.strokeWidth = l.width * measuredWidth
                canvas.drawLine(l.startX * measuredWidth, l.startY * measuredHeight, l.endX * measuredWidth, l.endY * measuredHeight, mPaint)
            }
        }
    }

    fun setTimeInterpolator(interpolator: TimeInterpolator) {
        this.interpolator = interpolator
    }

    fun start() {
        valueAnimator = valueAnimator ?: ValueAnimator.ofFloat(0f, 1f)
        valueAnimator?.duration = 1000
        valueAnimator?.addUpdateListener(this)
        interpolator?.let { valueAnimator?.setInterpolator(interpolator) }
        valueAnimator?.start()
    }

    /**
     * 为了释放资源
     */
    fun stop() {
        valueAnimator?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
        valueAnimator = null
    }

    fun addLine(line: Line) {
        lines = lines ?: ArrayList<Line>()
        lines?.add(line)
    }

    fun setLines(lines: MutableList<Line>) {
        this.lines = lines
    }

    fun removeLine(line: Line) {
        lines?.remove(line)
    }

    fun removeLine(index: Int) {
        lines?.removeAt(index)
    }

    /**
     * 动画到指定位置
     *
     * @param value 数值0-1
     */
    fun seekTo(value: Float) {
        var valueFun = value
        if (0 > valueFun) {
            valueFun = 0f
        } else if (valueFun > 1) {
            valueFun = 1f
        }
        refreshValue(valueFun)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        refreshValue(animation.animatedValue as Float)
    }

    private fun refreshValue(value: Float) {
        lines?.let {
            for (l in it) {
                l.onAinmated(value)
            }
            invalidate()
        }
    }

    abstract class Line {
        var startX: Float = 0.toFloat()
        var startY: Float = 0.toFloat()
        var endX: Float = 0.toFloat()
        var endY: Float = 0.toFloat()
        var width: Float = 0.toFloat()
        var color: Int = 0

        constructor() {}

        constructor(startX: Float, startY: Float, endX: Float, endY: Float, width: Float, color: Int) {
            this.startX = startX
            this.startY = startY
            this.endX = endX
            this.endY = endY
            this.width = width
            this.color = color
        }

        constructor(width: Float) {
            this.width = width
        }

        /**
         * 动画，根据
         * value 把startX startY endX endY和width计算一下
         *
         * @param value 0-1相当于时间
         */
        abstract fun onAinmated(value: Float)
    }
}
