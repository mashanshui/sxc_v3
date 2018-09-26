package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.sxcapp.www.R;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帮助中心界面
 * Created by wenleitao on 2017/8/9.
 */

public class HelpCenterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.re01)
    RelativeLayout re01;
    @BindView(R.id.re02)
    RelativeLayout re02;
    @BindView(R.id.re03)
    RelativeLayout re03;
    @BindView(R.id.re04)
    RelativeLayout re04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpcenter);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("帮助中心", null);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        re01.setOnClickListener(this);
        re02.setOnClickListener(this);
        re03.setOnClickListener(this);
        re04.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re01:
                openWebView("http://106.14.135.110/SxcH5/helpbeginner.html", "新手指引", true);
                break;
            case R.id.re02:
                openWebView("http://106.14.135.110/SxcH5/agreement.html", "会员协议", true);
                break;
            case R.id.re03:
                openWebView("http://106.14.135.110/SxcH5/helpbuycar.html", "计费规程", true);
                break;
            case R.id.re04:
                openWebView("http://106.14.135.110/SxcH5/helprentcar.html", "充值协议", true);
                break;
            default:
                break;
        }
    }
}
