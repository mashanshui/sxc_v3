package com.sxcapp.www.UserCenter.EventList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.SnapShotDetailBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/28.
 */

public class SnapShotActivityV3 extends BaseActivity {
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.result_lin)
    LinearLayout result_lin;
    @BindView(R.id.enter_btn)
    Button enter_btn;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.activity_iv)
    ImageView activity_iv;
    private String theme_id;
    private String user_id;
    private UserCenterService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot_v3);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle(getIntent().getStringExtra("theme_tittle"), null);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        theme_id = getIntent().getStringExtra("theme_id");
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<SnapShotDetailBeanV3>> ob = service.getSnapShotDetailV3(theme_id, user_id);
        ob.compose(compose(this.<CodeResultV3<SnapShotDetailBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<SnapShotDetailBeanV3>(this) {
            @Override
            protected void onHandleSuccess(SnapShotDetailBeanV3 beanV3) {
                removeProgressDlg();
                time_tv.setText(beanV3.getTerm_time());
                if (beanV3.getTheme_flag() == 0) {
                    enter_btn.setVisibility(View.GONE);
                } else {
                    enter_btn.setVisibility(View.VISIBLE);
                }
                Glide.with(SnapShotActivityV3.this).load(beanV3.getTheme_image()).into(activity_iv);
                if (beanV3.getContent().size() > 0) {
                    for (int i = 0; i < beanV3.getContent().size(); i++) {
                        TextView tv = new TextView(SnapShotActivityV3.this);
                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tv.setText((i + 1) + "." + beanV3.getContent().get(i));
                        tv.setLayoutParams(layoutParams);
                        tv.setPadding(0, 5, 0, 5);
                        tv.setTextColor(getResources().getColor(R.color.black_tv_26));
                        tv.setTextSize(15);
                        rules_lin.addView(tv, i);
                    }
                }
                if (beanV3.getAward_remark().size() > 0) {
                    for (int i = 0; i < beanV3.getAward_remark().size(); i++) {
                        TextView tv = new TextView(SnapShotActivityV3.this);
                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tv.setText((i + 1) + "." + beanV3.getAward_remark().get(i));
                        tv.setLayoutParams(layoutParams);
                        tv.setPadding(0, 5, 0, 5);
                        tv.setTextColor(getResources().getColor(R.color.black_tv_26));
                        tv.setTextSize(15);
                        result_lin.addView(tv, i);
                    }
                }
                enter_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SnapShotActivityV3.this, UploadSnapShotActivityV3.class);
                        intent.putExtra("theme_id", theme_id);
                        intent.putExtra("theme_tittle", getIntent().getStringExtra("theme_tittle"));
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
