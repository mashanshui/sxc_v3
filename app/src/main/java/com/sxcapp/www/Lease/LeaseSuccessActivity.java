package com.sxcapp.www.Lease;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.MyOrderDetailActivity;
import com.sxcapp.www.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 租车成功界面
 * Created by wenleitao on 2017/8/7.
 */

public class LeaseSuccessActivity extends BaseActivity {
    @BindView(R.id.to_main_btn)
    Button to_main_btn;
    @BindView(R.id.check_order_btn)
    Button check_order_btn;
    private String order_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_success);
        ButterKnife.bind(this);
        setTopBarTitle("租车成功", null);
        setTopbarLeftBackBtn();
        order_id = getIntent().getStringExtra("order_id");
//        跳转回主界面
        to_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().gotoMainActivity();
            }
        });
//        查看订单详情
        check_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaseSuccessActivity.this, MyOrderDetailActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("from", "success");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
