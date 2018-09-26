package com.sxcapp.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuideActivity extends BaseActivity implements OnGestureListener {
    @BindView(R.id.flipper)
    ViewFlipper mViewFlipper;
    @BindView(R.id.guide_iv03)
    ImageView iv03;
    private GestureDetector myGestureDetector;
    long exitTime;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        myGestureDetector = new GestureDetector(this);
        iv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return myGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
                           float arg3) {
        // TODO Auto-generated method stub
        int tag = Integer.parseInt(mViewFlipper.getCurrentView().getTag() + "");

        if (e1.getX() < e2.getX()) {
            if (tag == 1) {
                return false;
            }

            mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_right_in);
            mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_right_out);
            mViewFlipper.showPrevious();
            tag--;


        } else if (e1.getX() > e2.getX()) {
            if (tag == 3) {

            } else {
                mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_left_in);
                mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_left_out);
                mViewFlipper.showNext();
            }

            tag++;

        }

        if (tag > 2) {
            tag = 2;
        }
        if (tag < 1) {
            tag = 1;
        }


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
                            float arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something what you want
            ExitApp();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getResources().getString(R.string.touch_to_exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().systemExit();
        }
    }
}
