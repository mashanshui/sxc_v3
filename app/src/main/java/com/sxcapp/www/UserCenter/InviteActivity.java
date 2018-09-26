package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.InviteInfo;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.ShareUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 邀请好友界面
 * Created by wenleitao on 2017/8/10.
 */

public class InviteActivity extends BaseActivity {
    @BindView(R.id.content_lin)
    LinearLayout content_lin;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.invite_btn)
    Button invite_btn;
    private UserCenterService service;
    private ViewGroup.LayoutParams params;
    private ShareUtil shareUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_invite);
        setStatusView(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("邀请好友", null);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        shareUtil = new ShareUtil(this);
        loadData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 模拟相应耗时逻辑
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void loadData() {
        showProgressDlg();
        Observable<Result<InviteInfo>> ob = service.getInviteData();
        ob.compose(compose(this.<Result<InviteInfo>>bindToLifecycle())).subscribe(new BaseObserver<InviteInfo>(this) {
            @Override
            protected void onHandleSuccess(InviteInfo inviteInfo) {
                removeProgressDlg();
                content_lin.setVisibility(View.VISIBLE);
                List<String> rules = inviteInfo.getRules();
                for (int i = 0; i < rules.size(); i++) {
                    TextView appointTime_tv = new TextView(InviteActivity.this);
                    String str = (rules.size() - i) + "." + rules.get(i);
                    appointTime_tv.setText(str);
                    appointTime_tv.setTextColor(Color.parseColor("#3c3c3c"));
                    appointTime_tv.setTextSize(14);
                    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    appointTime_tv.setLayoutParams(params);
                    appointTime_tv.setPadding(0, 10, 0, 0);
                    rules_lin.addView(appointTime_tv, 0);
                }
                final String title = inviteInfo.getInvite_title();
                final String content = inviteInfo.getInvite_desc();
                final String target_url = inviteInfo.getInvite_url();
                invite_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.shareConfig(title, content, target_url + SharedData.getInstance().getString(SharedData._user_id));
                    }
                });

            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                content_lin.setVisibility(View.GONE);
                removeProgressDlg();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("inviteActivityDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
