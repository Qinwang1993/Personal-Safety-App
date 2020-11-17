package com.sjsu.sister.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class CustomCircleProgressBar extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final String TAG = CustomCircleProgressBar.class.getSimpleName();
    private boolean isChangeCenterBitmap = true;
    private boolean isSustainedDraw = false;
    private boolean isStart = true;
    private boolean isDrawSmallCircle = true;
    private int smallCircleColor;
    private boolean isDrawArc = true;
    private int arcColor;
    private CompleteTimeCallBack completeTimeCallBack;
    private SurfaceHolder holder = null;
    private Canvas canvas;
    private Paint pPaint;
    private int px;
    private int py;
    private int radius;
    private int defaultRadius = 40;
    private float startAngle = 270;
    private float sweepAngle;
    private float angle, duration = 20;
    private int startBitmap;
    private int stopBitmap;
    private int centerBitmap_margin = 10;
    private int dp;
    private Bitmap bitmap;
    private boolean isGetBitmap = false;
    long a, b, calculateTime, sleepTime = 60, correctSleepTime;

    public CustomCircleProgressBar(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public void init() {
        holder = getHolder();
        holder.addCallback(this);
        if (!isInEditMode()) {
            setZOrderOnTop(true);
        }
        holder.setFormat(PixelFormat.TRANSLUCENT);
        pPaint = new Paint();
        pPaint.setAntiAlias(true);
        pPaint.setStrokeWidth(4);
        pPaint.setStyle(Paint.Style.STROKE);
        angle = startAngle;
        sweepAngle = 0;
        dp = Resources.getSystem().getDisplayMetrics().densityDpi / 160;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!isStart) {
            reset();
        }
        radius = defaultRadius * dp;
        isChangeCenterBitmap = true;
        px = this.getWidth() / 2;
        py = this.getHeight() / 2;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isStart = false;
    }

    @Override
    public void run() {
        while (isStart) {
            if (isSustainedDraw) {
                canvas = holder.lockCanvas();
                if (canvas == null) {
                    continue;
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                drawCenterBitmap();
                drawCircle();
                holder.unlockCanvasAndPost(canvas);

                calculateTime = calculateTime + sleepTime;
                try {
                    b = a;
                    a = System.currentTimeMillis();

                    if (b == 0) {
                        correctSleepTime = sleepTime;

                    } else {
                        if ((a - b) >= sleepTime && (a - b) < 2 * sleepTime) {
                            correctSleepTime = sleepTime - (a - b - correctSleepTime);
                        } else if ((a - b) > 2 * sleepTime) {
                            correctSleepTime = 0;
                            //不睡眠
                        } else if ((a - b) < sleepTime) {
                            correctSleepTime = sleepTime - (a - b - correctSleepTime);
                        }
                    }
                    if (correctSleepTime > 0) {
                        Thread.sleep(correctSleepTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (isChangeCenterBitmap) {
                canvas = holder.lockCanvas();
                if (canvas == null) {
                    continue;
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                drawCenterBitmap();
                drawCircle();
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void drawCircle() {
        pPaint.setColor(Color.LTGRAY);
        pPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(px, py, radius, pPaint);

        if (isDrawSmallCircle) {
            pPaint.setColor(smallCircleColor);
            pPaint.setStyle(Paint.Style.FILL);

            float ballX = (float) (px + radius * Math.cos(angle * Math.PI / 180));
            float ballY = (float) (py + radius * Math.sin(angle * Math.PI / 180));
            canvas.drawCircle(ballX, ballY, 4 * dp, pPaint);
        }

        if (isDrawArc) {
            pPaint.setStyle(Paint.Style.STROKE);
            pPaint.setColor(arcColor);
            RectF rect = new RectF(px - radius, py - radius, px + radius, py + radius);
            canvas.drawArc(rect, startAngle, sweepAngle, false, pPaint);//画弧形
        }

        float speed = 360 / (duration * (1000 / sleepTime));
        angle = angle + speed;
        if (angle > 360) {
            angle = 0;
        }
        sweepAngle = sweepAngle + speed;
        if (sweepAngle > 360) {
            reset();
            if (completeTimeCallBack != null) {
                completeTimeCallBack.stop();
            }
            isSustainedDraw = false;
            isChangeCenterBitmap = true;
        }
    }

    private void drawCenterBitmap() {
        int area = radius - centerBitmap_margin * dp;
        RectF imageRect = new RectF(px - area, py - area, px + area, py + area);

        if (isChangeCenterBitmap) {
            if (!isGetBitmap) {
                if (isSustainedDraw) {
                    bitmap = BitmapFactory.decodeResource(getResources(), stopBitmap);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), startBitmap);
                }
            }
            isChangeCenterBitmap = false;
        }
        canvas.drawBitmap(bitmap, null, imageRect, pPaint);
    }

    public interface CompleteTimeCallBack {
        void stop();
    }

    public void completeTime(CompleteTimeCallBack completeTimeCallBack) {
        this.completeTimeCallBack = completeTimeCallBack;
    }

    public void startDraw() {
        isChangeCenterBitmap = true;
        isSustainedDraw = true;
    }

    public void stopDraw() {
        isChangeCenterBitmap = true;
        isSustainedDraw = false;
    }

    public void reset() {
        isStart = true;
        angle = startAngle;
        sweepAngle = 0;
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
    }

    public void setSmallCircleColor(int smallCircleColor) {
        this.smallCircleColor = smallCircleColor;
    }

    public void setDefaultRadius(int defaultRadius) {
        this.defaultRadius = defaultRadius;
    }

    public void setStartBitmap(int startBitmap) {
        this.startBitmap = startBitmap;
    }

    public void setStopBitmap(int stopBitmap) {
        this.stopBitmap = stopBitmap;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}

