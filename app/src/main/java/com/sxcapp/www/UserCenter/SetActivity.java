package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;

/**
 * 退出登录界面
 * Created by wenleitao on 2017/8/8
 */

public class SetActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setTopBarTitle("设置",null);
        setTopbarLeftBackBtn();
        setStatusView(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        TextView version_tv =  findViewById(R.id.version_tv);
        version_tv.setText(AndroidTool.getVersionName());
        Button login_out_btn = findViewById(R.id.login_out_btn);
        login_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().confirmLogout(SetActivity.this);
            }
        });
    }
}
