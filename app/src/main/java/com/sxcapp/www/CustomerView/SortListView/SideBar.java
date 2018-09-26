package com.sxcapp.www.CustomerView.SortListView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wenleitao on 2017/7/5.
 */

public class SideBar extends View {
    //    触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChanged;
    //    26个索引字母
    public static String[] b =
            {"A", "B", "C", "D", "E", "F", "G", "H", "I",
                    "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                    "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;

    /**
     * 为slideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setmTextDialog(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 画每个字母
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        获取焦点改变背景颜色
        int height = getHeight();//获取对应高度
        int width = getWidth();//获取对应宽度
//        获取每一个字母的高度
        int singleHeight = height / b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.parseColor("#d43c33"));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }
    }

    /**
     * 触摸事件处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();//点击点y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChanged;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.parseColor("#00000000"));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundColor(Color.parseColor("#f4f4f4"));
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }

                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
         return  true;
    }

    public void setOnTouchingLetterChanged(OnTouchingLetterChangedListener onTouchingLetterChanged) {
        this.onTouchingLetterChanged = onTouchingLetterChanged;
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
         void onTouchingLetterChanged(String s);
    }

}
