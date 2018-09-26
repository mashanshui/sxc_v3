package com.sxcapp.www.Sell;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.sxcapp.www.R;
import com.sxcapp.www.activity.BaseActivity;

/**
 * Created by wenleitao on 2017/7/21.
 */

public class SellFlowActivity extends BaseActivity {
    private WebView web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_flow);
        setTopbarLeftBackBtn();
        setTopBarTitle("卖车流程", null);
        web = (WebView) findViewById(R.id.web);
        web.loadUrl("http://106.14.135.110:80/SxcH5/sellinfo.html");
    }
}
