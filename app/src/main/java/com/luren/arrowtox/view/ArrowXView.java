package com.luren.arrowtox.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator 可爱的路人 on 2017/12/15.
 * Email:513421345@qq.com
 * TODO
 */
public class ArrowXView extends LineAnimView {
    public ArrowXView(Context context) {
        super(context);
        init();
    }

    public ArrowXView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ArrowXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        addLine(new LineAnimView.Line(0.5f, 0f, 0f, 0.5f, 0.1f, Color.WHITE) {
            @Override
            public void onAinmated(float value) {
                // (0.5,0) -> (1, 0)   (0,0.5) -> (0.5, 0.5)
                setStartX(0.5f + value / 2);
                setEndX(value / 2);
            }
        });
        addLine(new LineAnimView.Line(-0.036f, 0.468f, 0f, 0.5f, 0.1f, Color.WHITE) {
            @Override
            public void onAinmated(float value) {
                // (0,0.5) -> (0, 0)   (0,0.5) -> (0.5, 0.5)
                setStartY(0.46f * (1 - value));
                setStartX(-0.04f * (1 - value));
                setEndX(value / 2);
            }
        });
        addLine(new LineAnimView.Line(0f, 0.5f, 0.5f, 1f, 0.1f, Color.WHITE) {
            @Override
            public void onAinmated(float value) {
                // (0,0.5) -> (0.5, 0.5)   (0.5,1) -> (0, 1)
                setStartX(value / 2);
                setEndX(0.5f - value / 2);
            }
        });
        addLine(new LineAnimView.Line(0f, 0.5f, 0.8f, 0.5f, 0.1f, Color.WHITE) {
            @Override
            public void onAinmated(float value) {
                // (1,0.5) -> (1, 1)   (1,0.5) -> (1, 1)
                setStartX(value / 2);
                double endYcal = 0.5 + value / 2;
                double angel = Math.atan((endYcal - 0.5) / (1 - getStartX()));
                setEndY((float) (0.5 + value * 0.5 - 0.2 * Math.sin(angel)));
                setEndX((float) (1 - 0.2 * Math.cos(angel)));
            }
        });
        addLine(new LineAnimView.Line(0.9f, 0.5f, 1f, 0.5f, 0.1f, Color.CYAN) {
            @Override
            public void onAinmated(float value) {
                // (0,0.5) -> (0.5, 0.5)   (1,0.5) -> (1, 1)
                double endYcal = 0.5 + value / 2;
                double angel = Math.atan((endYcal - 0.5) / (1 - value / 2));
                setStartY((float) (0.5 + value * 0.5 - 0.1 * Math.sin(angel)));
                setStartX((float) (1 - 0.1 * Math.cos(angel)));
                setEndY(0.5f + value / 2);
            }
        });
    }
}