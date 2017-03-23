package net.hivebc.waterprogress.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import net.hivebc.waterprogress.R;


/**
 * Created by albert on 17-3-13.
 */

public class WaterProgress extends View {
    public static final int defaultDiam = 100;
    public static final int MIN_DIAM_SIZE = 20;
    private final int maxProgress = 100;
    Paint paint = new Paint();
    Path path = new Path();
    private int progress;
    private boolean showProgress;
    private float speed;
    private float wave;
    private float diam;
    private float radius;
    private float xCircle;
    private float yCircle;
    private int color;
    private int backgroundColor;
    private int textColor;
    private int offset = 1;
    private int strokeWidth = dp2px(4);
    private float textSize = dp2px(20);

    public WaterProgress(Context context) {
        super(context);
    }

    public WaterProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaterProgress, defStyleAttr, 0);
        init(a);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaterProgress, defStyleAttr, defStyleRes);
        init(a);
    }

    private void init(TypedArray a) {
        speed = a.getFloat(R.styleable.WaterProgress_speed, 1f);
        wave = a.getFloat(R.styleable.WaterProgress_wave, 1f);
        showProgress = a.getBoolean(R.styleable.WaterProgress_showProgress, true);
        color = a.getColor(R.styleable.WaterProgress_color, Color.CYAN);
        backgroundColor = a.getColor(R.styleable.WaterProgress_backgroundColor, Color.WHITE);
        textSize = a.getDimension(R.styleable.WaterProgress_textSize, dp2px(20));
        textColor = a.getColor(R.styleable.WaterProgress_textColor, Color.BLACK);
        float diam = a.getDimension(R.styleable.WaterProgress_diam, dp2px(defaultDiam));
        setDiam(diam);
        a.recycle();
        radius = diam / 2f;
        xCircle = radius + strokeWidth;
        yCircle = radius + strokeWidth;
    }

    public synchronized void setSpeed(@FloatRange(from = 0.0, to = 1f) float speed) {
        this.speed = speed;
    }

    public synchronized void setWave(@FloatRange(from = 0.0, to = 1f) float wave) {
        this.wave = wave;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) diam + strokeWidth * 2, (int) diam + strokeWidth * 2);
    }

    public void setDiam(float diam) {
        if (diam < dp2px(MIN_DIAM_SIZE))
            diam = dp2px(MIN_DIAM_SIZE);
        this.diam = diam;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawWave(canvas);
        if (showProgress) {
            drawProgress(canvas);
        }
        postInvalidateDelayed(5);
        if (offset < diam) {
            offset += 10 * speed;
        } else {
            offset = 0;
        }
    }

    private void drawProgress(Canvas canvas) {
        paint.reset();
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        String text = (int) getPercent(progress) + "%";
        Paint.FontMetrics fm = paint.getFontMetrics();
        canvas.drawText(text, xCircle, yCircle - fm.descent + Math.abs(fm.bottom - fm.top) / 2, paint);
    }

    private void drawWave(Canvas canvas) {
        path.reset();
        path.addCircle(xCircle, yCircle, radius, Path.Direction.CCW);
        canvas.clipPath(path);
        buildWave(path);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }

    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(xCircle, yCircle, radius, paint);
        paint.reset();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xCircle, yCircle, radius, paint);
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0)
            return;
        if (progress > maxProgress)
            progress = maxProgress;
        this.progress = progress;
        postInvalidate();
    }

    private void buildWave(@NonNull Path path) {
        // wave path
        path.reset();
        path.moveTo(-diam + offset, diam + strokeWidth * 2);
        float levelHeight = getLevelHeight(progress);
        float quarterDiameter = diam / 4f;
        path.lineTo(-diam + offset, levelHeight);
        path.quadTo(-diam * 3f / 4 + offset, levelHeight + quarterDiameter * wave, -diam / 2f + offset, levelHeight);
        path.quadTo(-quarterDiameter + offset, levelHeight - quarterDiameter * wave, offset, levelHeight);
        path.quadTo(quarterDiameter + offset, levelHeight + quarterDiameter * wave, diam / 2f + offset, levelHeight);
        path.quadTo(diam * 3f / 4 + offset, levelHeight - quarterDiameter * wave, diam + strokeWidth * 2 + offset, levelHeight);
        path.lineTo(diam + strokeWidth * 2 + offset, diam + strokeWidth * 2);
        path.close();
    }

    private float getLevelHeight(int progress) {
        return diam * (1 - getPercent(progress) / 100);
    }

    private float getPercent(int progress) {
        return progress * 1f / maxProgress * 100;
    }

    public int dp2px(int value) {
        final float scale = getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }
}
