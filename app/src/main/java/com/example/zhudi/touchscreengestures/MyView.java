package com.example.zhudi.touchscreengestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhudi on 15/10/27.
 */
public class MyView extends View {
    private static final String TAG = "MyView";
    //private float x;
    //private float y;
    private List<PointF> mPoints = new ArrayList<>();
    private List<Integer> mIdList = new ArrayList<>();
    private static final float RADIUS = 125;
    private Paint textPaint;
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW};

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < mPoints.size(); i++) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(colors[i % colors.length]);
            canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, RADIUS, paint);
            canvas.drawText("Pointer ID: " + mIdList.get(i) +
                            " Current X coordinate: " + mPoints.get(i).x +
                            " Current Y coordinate: " + mPoints.get(i).y,
                    30, 45 * (i + 1), textPaint);
        }
        Log.d(TAG, "onDraw() is called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        int pointIndex = event.getActionIndex();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF point = new PointF();
                point.x = event.getX(pointIndex);
                point.y = event.getY(pointIndex);
                mIdList.add(pointIndex, pointIndex);
                mPoints.add(pointIndex, point);
                Log.d(TAG, "ACTION_DOWN");
                Log.d(TAG, "x = " + point.x);
                Log.d(TAG, "y = " + point.y);
                break;

            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < pointerCount; i++) {
                    PointF p = mPoints.get(i);
                    if(p != null) {
                        p.x = event.getX(i);
                        p.y = event.getY(i);
                        mPoints.set(i, p);
                    }
                }
                Log.d(TAG, "ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                mPoints.remove(pointIndex);
                break;
        }
        invalidate();


        return true;
    }

}
