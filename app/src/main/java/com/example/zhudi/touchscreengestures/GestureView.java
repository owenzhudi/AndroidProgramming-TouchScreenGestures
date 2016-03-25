package com.example.zhudi.touchscreengestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhudi on 15/10/29.
 */
public class GestureView extends View {
    private static final String TAG = GestureView.class.getSimpleName();
    private Paint mPaint;
    private Paint mTextPaint;
    private float mCurrentX;
    private float mCurrentY;
    private int id;
    private Path mPath;

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("Pointer ID: " + id +
                    " Current X coordinate: " + mCurrentX +
                    " Current Y coordinate: " + mCurrentY,
                30, 45, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        id = event.getPointerId(0);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(mCurrentX, mCurrentY);
                Log.d(TAG, "ACTION_DOWN");
                this.invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(mCurrentX, mCurrentY);
                Log.d(TAG, "ACTION_MOVE");
                this.invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                return true;

            default:
                return false;
        }
    }

    public void clear() {
        mPath.reset();
        invalidate();
    }
}
