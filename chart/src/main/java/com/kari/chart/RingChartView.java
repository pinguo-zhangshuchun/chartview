package com.kari.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * File:   RingChartView.java
 * Author: kari
 * Date:   17-4-5 on 22:30
 */
public class RingChartView extends ChartView {

    public final static int CLOCKWISE = 0;
    public final static int ANTICLOCKWISE = 1;

    private int mDirection;
    private int mAngleFrom;
    private int mSandwich;

    public RingChartView(Context context) {
        this(context, null);
    }

    public RingChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingChartView);
        mDirection = ta.getInteger(R.styleable.RingChartView_direction, 0);
        mAngleFrom = ta.getInteger(R.styleable.RingChartView_angle_from, 0);
        mSandwich = ta.getDimensionPixelOffset(R.styleable.RingChartView_sandwich, 4);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int summary = getSummary();
        if (summary <= 0) {
            return;
        }

        int angleFrom = mAngleFrom;
        int angleSweep;
        for (int i = 0; i < mChartInfo.size(); i++) {
            mPaint.setColor(mChartInfo.get(i).getColor());
            angleSweep = (int) (mChartInfo.get(i).getValue() / (float) summary * 360);
            mRectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            canvas.drawArc(mRectF, angleFrom, angleSweep, true, mPaint);

            mRectF.left += mSandwich;
            mRectF.right -= mSandwich;
            mRectF.top += mSandwich;
            mRectF.bottom -= mSandwich;
            mPaint.setColor(mBgColor);
            canvas.drawArc(mRectF, angleFrom, angleSweep, true, mPaint);

            angleFrom += angleSweep;
        }
    }

}
