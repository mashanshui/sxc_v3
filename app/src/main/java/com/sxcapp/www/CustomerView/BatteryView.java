package com.sxcapp.www.CustomerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sxcapp.www.R;


/**
 * Created by wenleitao on 2018/4/18.
 */


public class BatteryView extends View {

    private int mPower = 100;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int battery_left = 0;
        int battery_top = 0;
        int battery_width = getMeasuredWidth();
        int battery_height = getMeasuredHeight();

        int battery_head_width = 3;
        int battery_head_height = 3;

        int battery_inside_margin = 3;
        float power_percent = mPower / 100.0f;
        Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor("#259b24"));
        paint2.setStyle(Paint.Style.FILL);
        //画电量
        if (power_percent != 0) {
            int p_left = battery_left + battery_inside_margin;
            int p_top = battery_top + battery_inside_margin;
            int p_right = p_left - battery_inside_margin + (int) ((battery_width - battery_inside_margin) * power_percent);
            int p_bottom = p_top + battery_height - battery_inside_margin * 2;
            Rect rect2 = new Rect(p_left, p_top, p_right, p_bottom);
            canvas.drawRect(rect2, paint2);
        }

        //画电池头
    }

    public void setPower(int power) {
        mPower = power;
        if (mPower < 0) {
            mPower = 0;
        }
        invalidate();
    }
}
