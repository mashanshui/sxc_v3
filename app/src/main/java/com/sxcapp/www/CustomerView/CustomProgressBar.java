package com.sxcapp.www.CustomerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.sxcapp.www.R;


public class CustomProgressBar extends ProgressBar {

    private String text_progress;
    private Paint mPaint;//画笔

    public CustomProgressBar(Context context) {
        super(context);
        initPaint();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
//        setTextProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub  
        super.onDraw(canvas);
//        Rect rect=new Rect();
//        this.mPaint.getTextBounds(this.text_progress, 0, this.text_progress.length(), rect);
//        int x = (getWidth() / 2) - rect.centerX();
//        int y = (getHeight() / 2) - rect.centerY();
//        canvas.drawText(this.text_progress, x, y, this.mPaint);
    }

    /**
     * description: 初始化画笔
     * Create by lll on 2013-8-13 下午1:41:49
     */
    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(getResources().getColor(R.color.orRed));
    }

    private void setTextProgress(int progress) {
        int i = (int) ((progress * 1.0f / this.getMax()) * 100);
        this.text_progress = String.valueOf(i) + "%";
    }
}
