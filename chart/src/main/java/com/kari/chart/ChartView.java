package com.kari.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * File:   ChartView.java
 * Author: kari
 * Date:   17-4-5 on 22:13
 */
public class ChartView extends View {

    final static String TAG = "ChartView";

    protected Paint mPaint;
    protected int mBgColor;
    protected RectF mRectF;

    protected List<ChartInfo> mChartInfo;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mChartInfo = new ArrayList<>();
    }

    protected void init() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();

        Drawable drawable = getBackground();
        if (drawable != null && drawable instanceof ColorDrawable) {
            mBgColor = ((ColorDrawable) drawable).getColor();
        } else {
            mBgColor = Color.parseColor("#ffffff");
        }
    }

    public void setChartInfo(List<ChartInfo> info) {
        mChartInfo.clear();
        if (info != null) {
            mChartInfo.addAll(info);
        }
        invalidate();
    }

    public int getSummary() {
        int count = 0;
        for (ChartInfo info : mChartInfo) {
            count += info.value;
        }
        return count;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Bitmap bitmap = convertViewToBitmap(this);
            if (bitmap == null) {
                Log.e(TAG, "Failed getDrawingCache !");
                return true;
            }

            if (x >= bitmap.getWidth() || y >= bitmap.getWidth()) {
                return false;
            }

            int color = bitmap.getPixel(x, y);
            for (int i = 0; i < mChartInfo.size(); i++) {
                if (mChartInfo.get(i).color == color) {
                    Log.d(TAG, "hit " + i);
                }
            }
        }

        return false;
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static class ChartInfo {
        private int value;
        private int color;

        private ChartInfo(int value, int color) {
            this.color = color;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int getColor() {
            return color;
        }

        public static List<ChartInfo> build(int[] values, int[] colors) {
            if (null == values || values.length < 1 ||
                    null == colors || colors.length < 1 ||
                    colors.length != values.length) {
                throw new IllegalArgumentException("Invalid arguments !");
            }

            List<ChartInfo> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                list.add(new ChartInfo(values[i], colors[i]));
            }

            return list;
        }

        public static List<ChartInfo> build(int[] values, List<String> colors) {
            if (null == values || values.length < 1 ||
                    null == colors || colors.size() < 1 ||
                    colors.size() != values.length) {
                throw new IllegalArgumentException("Invalid arguments !");
            }

            List<ChartInfo> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                list.add(new ChartInfo(values[i], Color.parseColor(colors.get(i))));
            }

            return list;
        }

        public static List<ChartInfo> build(int[] values, String[] colors) {
            if (null == values || values.length < 1 ||
                    null == colors || colors.length < 1 ||
                    colors.length != values.length) {
                throw new IllegalArgumentException("Invalid arguments !");
            }

            List<ChartInfo> list = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                list.add(new ChartInfo(values[i], Color.parseColor(colors[i])));
            }

            return list;
        }
    }

}
