package com.luren.arrowtox.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator 可爱的路人 on 2017/12/15.
 * Email:513421345@qq.com
 * TODO
 */
public class LineAnimView extends View implements ValueAnimator.AnimatorUpdateListener {
    private List<Line> lines;
    private ValueAnimator valueAnimator;
    private TimeInterpolator interpolator;
    private Paint mPaint;

    public LineAnimView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public LineAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public LineAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    public LineAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int measuredHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        for (Line l : lines) {
            mPaint.setColor(l.color);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(l.width * measuredWidth);
            canvas.drawLine(l.startX * measuredWidth, l.startY * measuredHeight
                    , l.endX * measuredWidth, l.endY * measuredHeight, mPaint);
        }
    }

    public void setTimeInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void start() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(this);
        }
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.start();
    }

    /**
     * 为了释放资源
     */
    public void stop() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
        valueAnimator = null;
    }

    public void addLine(Line line) {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        lines.add(line);
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void removeLine(Line line) {
        if (lines != null) {
            lines.remove(line);
        }
    }

    public void removeLine(int index) {
        if (lines != null) {
            lines.remove(index);
        }
    }

    /**
     * 动画到指定位置
     *
     * @param value 数值0-1
     */
    public void seekTo(float value) {
        if (0 > value) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        refreshValue(value);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        refreshValue((Float) animation.getAnimatedValue());
    }

    private void refreshValue(float value) {
        if (lines == null || lines.size() == 0) {
            return;
        }
        for (Line l : lines) {
            l.onAinmated(value);
        }
        invalidate();
    }


    public static abstract class Line {
        private float startX;
        private float startY;
        private float endX;
        private float endY;
        private float width;
        private int color;

        public Line() {
        }

        public Line(float startX, float startY, float endX, float endY, float width, int color) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.width = width;
            this.color = color;
        }

        public Line(float width) {
            this.width = width;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public void setStartY(float startY) {
            this.startY = startY;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public void setEndY(float endY) {
            this.endY = endY;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public float getStartX() {
            return startX;
        }

        public float getStartY() {
            return startY;
        }

        public float getEndX() {
            return endX;
        }

        public float getEndY() {
            return endY;
        }

        public float getWidth() {
            return width;
        }

        public int getColor() {
            return color;
        }

        /**
         * 动画，根据
         * value 把startX startY endX endY和width计算一下
         *
         * @param value 0-1相当于时间
         */
        public abstract void onAinmated(float value);
    }
}
