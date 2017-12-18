package com.luren.arrowtox.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet

/**
 * Created by Administrator 可爱的路人 on 2017/12/18.
 * Email:513421345@qq.com
 * TODO
 */
class ArrowXViewKt : LineAnimView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        addLine(object : LineAnimView.Line(0.5f, 0f, 0f, 0.5f, 0.1f, Color.WHITE) {
            override fun onAinmated(value: Float) {
                // (0.5,0) -> (1, 0)   (0,0.5) -> (0.5, 0.5)
                startX = 0.5f + value / 2
                endX = value / 2
            }
        })
        addLine(object : LineAnimView.Line(-0.036f, 0.468f, 0f, 0.5f, 0.1f, Color.WHITE) {
            override fun onAinmated(value: Float) {
                // (0,0.5) -> (0, 0)   (0,0.5) -> (0.5, 0.5)
                startY = 0.46f * (1 - value)
                startX = -0.04f * (1 - value)
                endX = value / 2
            }
        })
        addLine(object : LineAnimView.Line(0f, 0.5f, 0.5f, 1f, 0.1f, Color.WHITE) {
            override fun onAinmated(value: Float) {
                // (0,0.5) -> (0.5, 0.5)   (0.5,1) -> (0, 1)
                startX = value / 2
                endX = 0.5f - value / 2
            }
        })
        addLine(object : LineAnimView.Line(0f, 0.5f, 0.8f, 0.5f, 0.1f, Color.WHITE) {
            override fun onAinmated(value: Float) {
                // (1,0.5) -> (1, 1)   (1,0.5) -> (1, 1)
                startX = value / 2
                val endYcal = 0.5 + value / 2
                val angel = Math.atan((endYcal - 0.5) / (1 - startX))
                endY = (0.5 + value * 0.5 - 0.2 * Math.sin(angel)).toFloat()
                endX = (1 - 0.2 * Math.cos(angel)).toFloat()
            }
        })
        addLine(object : LineAnimView.Line(0.9f, 0.5f, 1f, 0.5f, 0.1f, Color.CYAN) {
            override fun onAinmated(value: Float) {
                // (0,0.5) -> (0.5, 0.5)   (1,0.5) -> (1, 1)
                val endYcal = 0.5 + value / 2
                val angel = Math.atan((endYcal - 0.5) / (1 - value / 2))
                startY = (0.5 + value * 0.5 - 0.1 * Math.sin(angel)).toFloat()
                startX = (1 - 0.1 * Math.cos(angel)).toFloat()
                endY = 0.5f + value / 2
            }
        })
    }
}
