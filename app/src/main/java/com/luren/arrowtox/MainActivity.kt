package com.luren.arrowtox

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import com.luren.arrowtox.view.CompleteView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        axvt.setBackgroundColor(Color.GRAY)
        axvt.setTimeInterpolator(LinearInterpolator())
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                axvt.seekTo(progress.toFloat() / 100)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    fun selfAnim(v: View) {
        axvt.start()
        val dialog = Dialog(this)
        val completeView = CompleteView(this)
        completeView.setCircleStrokeWidth(5)
        completeView.setTickStrokeWidth(8)

        dialog.setContentView(completeView)
        dialog.window.attributes.width =250
        dialog.window.attributes.height =250
        dialog.show()
    }
}
