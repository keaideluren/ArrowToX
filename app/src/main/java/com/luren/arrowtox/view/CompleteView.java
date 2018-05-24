package com.luren.arrowtox.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.luren.arrowtox.R;

/**
 * Created by Administrator 可爱的路人 on 2018/5/24.
 * Email:513421345@qq.com
 * TODO
 */
public class CompleteView extends View {
    public CompleteView(Context context) {
        super(context);
        init(context, null);
    }

    public CompleteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CompleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private PathMeasure tickPathMeasure;
    /**
     * 打钩百分比
     */
    float tickPercent = 0;

    private Path path;
    //初始化打钩路径
    private Path tickPath;


    // 圆圈的大小,半径
    private int circleColor;
    private int circleStrokeWidth;
    private int tickStrokeWidth;

    private Paint tickPaint;
    private Paint circlePaint;

    public void init(Context context, AttributeSet attrs) {

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CompleteView);
        circleColor = mTypedArray.getColor(R.styleable.CompleteView_circleViewColor, ContextCompat.getColor(context, R.color.colorPrimary));
        circleStrokeWidth = mTypedArray.getInteger(R.styleable.CompleteView_circleStrokeWidth, 20);
        tickStrokeWidth = mTypedArray.getInteger(R.styleable.CompleteView_circleTickWidth, 20);
        mTypedArray.recycle();

        tickPaint = new Paint();
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeWidth(circleStrokeWidth);

        tickPathMeasure = new PathMeasure();
        path = new Path();
        tickPath = new Path();
        tickPaint.setStyle(Paint.Style.STROKE);
        tickPaint.setAntiAlias(true);
        tickPaint.setColor(circleColor);
        tickPaint.setStrokeWidth(tickStrokeWidth);

        //打钩动画
        ValueAnimator mTickAnimation;
        mTickAnimation = ValueAnimator.ofFloat(0f, 1f);
        mTickAnimation.setStartDelay(500);
        mTickAnimation.setDuration(500);
        mTickAnimation.setInterpolator(new AccelerateInterpolator());
        mTickAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tickPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mTickAnimation.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        width -= getPaddingLeft() + getPaddingRight();
        width -= getPaddingTop() + getPaddingBottom();

        if (width > height) {
            canvas.translate((width - height) / 2 + getPaddingLeft(), 0);
        } else {
            canvas.translate(0, (height - width) / 2 + getPaddingTop());
        }
        // 根据设置该view的高度，进行对所画图进行居中处理
        float circleRadius = Math.min(height, width);

        // 设置第一条直线的相关参数
        float firStartX = circleRadius * 5 / 19;
        float firStartY = circleRadius * 9 / 19;

        float firEndX = circleRadius * 8 / 19;
        float firEndY = circleRadius * 12 / 19;

        float secEndX = circleRadius * 14 / 19;
        float secEndY = circleRadius * 6.4f / 19;

        tickPath.moveTo(firStartX, firStartY);
        tickPath.lineTo(firEndX, firEndY);
        tickPath.lineTo(secEndX, secEndY);
        tickPathMeasure.setPath(tickPath, false);
        /*
         * On KITKAT and earlier releases, the resulting path may not display on a hardware-accelerated Canvas.
         * A simple workaround is to add a single operation to this path, such as dst.rLineTo(0, 0).
         */
        tickPathMeasure.getSegment(0, tickPercent * tickPathMeasure.getLength(), path, true);
        canvas.drawPath(path, tickPaint);
        canvas.drawArc(circleStrokeWidth / 2, circleStrokeWidth / 2, circleRadius - circleStrokeWidth / 2
                , circleRadius - circleStrokeWidth / 2, 0, 360, false, circlePaint);
    }

}
