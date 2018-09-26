package com.sxcapp.www.CustomerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sxcapp.www.R;

import java.lang.ref.SoftReference;

/**
 * Created by wenleitao on 2017/7/12.
 */

public class AppLoadingView extends ImageView {

    private MyRunnable runnable;
    private int width;
    private int height;

    public AppLoadingView(Context context) {
        super(context);
        init();
    }

    public AppLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        runnable = null;
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.loading_circle);
        setImageBitmap(bitmap);
        width = bitmap.getWidth() / 2;
        height = bitmap.getHeight() / 2;
        runnable = new AppLoadingView.MyRunnable(this);
    }

    public void startLoad() {
        if (runnable != null) {
            runnable.startload();
        } else {
            runnable = new AppLoadingView.MyRunnable(this);
            runnable.startload();
        }
    }

    public void stopLoad() {
        if (runnable != null) {
            runnable.stopload();
        }
    }

    static class MyRunnable implements Runnable {
        private boolean flag;
        private SoftReference<AppLoadingView> loadingViewSoftReference;
        private float degrees = 0f;
        private Matrix max;

        public MyRunnable(AppLoadingView loadingView) {
            loadingViewSoftReference = new SoftReference<>(loadingView);
            max = new Matrix();
        }

        @Override
        public void run() {
            if (loadingViewSoftReference.get().runnable != null && max != null) {
                degrees += 30f;
                max.setRotate(degrees, loadingViewSoftReference.get().width, loadingViewSoftReference.get().height);
                loadingViewSoftReference.get().setImageMatrix(max);
                if (degrees == 360) {
                    degrees = 0;
                }
                if (flag) {
                    loadingViewSoftReference.get().postDelayed(loadingViewSoftReference.get().runnable, 50);
                }
            }
        }

        public void stopload() {
            flag = false;
        }

        public void startload() {
            flag = true;
            if (loadingViewSoftReference.get().runnable != null && max != null) {
                loadingViewSoftReference.get().postDelayed(loadingViewSoftReference.get().runnable, 50);
            }
        }
    }
}
