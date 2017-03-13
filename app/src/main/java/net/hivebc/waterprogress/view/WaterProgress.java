package net.hivebc.waterprogress.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by albert on 17-3-13.
 */

public class WaterProgress extends View {
    public WaterProgress(Context context) {
        super(context);
    }

    public WaterProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int progress;
    private int maxProgress;
    Paint paint = new Paint();
    Path path = new Path();
    private int count = 1;

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(4);
        canvas.drawColor(Color.GRAY);
        path.reset();
        path.addCircle(150,100,100, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawCircle(150, 100, 104, paint);
        canvas.save();
        canvas.translate(count,0);
        path.reset();
        path.moveTo(0, 100);
        path.quadTo(50, 50, 100, 100);
        path.quadTo(160 , 150, 250 , 100);
        canvas.drawPath(path, paint);
        canvas.restore();
        postInvalidateDelayed(5);
        if (count < 50) count++;
        else count = 1;
    }

    public void setProgress(int progress) {
        if (progress < 0)
            return;
        if (progress > maxProgress)
            progress = maxProgress;
        invalidate();
    }

}
