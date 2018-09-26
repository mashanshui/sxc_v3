package com.sxcapp.www.UserCenter.EventList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.EventCountBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class EventGuideActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.elec_short_re)
    RelativeLayout elec_short_re;
    @BindView(R.id.elec_inday_re)
    RelativeLayout elec_inday_re;
    @BindView(R.id.oil_short_re)
    RelativeLayout oil_short_re;
    @BindView(R.id.oil_inday_re)
    RelativeLayout oil_inday_re;
    @BindView(R.id.luxury_re)
    RelativeLayout luxury_re;
    @BindView(R.id.oil_inday_event_count_tv)
    TextView oil_inday_event_count_tv;
    @BindView(R.id.oil_short_event_count_tv)
    TextView oil_short_event_count_tv;
    @BindView(R.id.elec_inday_event_count_tv)
    TextView elec_inday_event_count_tv;
    @BindView(R.id.elec_short_event_count_tv)
    TextView elec_short_event_count_tv;
    @BindView(R.id.luxury_event_count_tv)
    TextView luxury_event_count_tv;
    @BindView(R.id.snapshot_re)
    RelativeLayout snapshot_re;
    @BindView(R.id.activity_tittle_tv)
    TextView activity_tittle_tv;
    private UserCenterService service;
    private int oilTime_count;
    private int oilLong_count;
    private int luxury_count;
    private int eleTime_count;
    private int eleLong_count;
    private String theme_id, theme_tittle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_guide_v3);
        ButterKnife.bind(this);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("活动指引", null);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        loaData();
    }

    private void loaData() {
        showProgressDlg();
        Observable<CodeResultV3<EventCountBeanV3>> ob = service.getEventCountV3();
        ob.compose(compose(this.<CodeResultV3<EventCountBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<EventCountBeanV3>(this) {
            @Override
            protected void onHandleSuccess(EventCountBeanV3 beanV3) {
                removeProgressDlg();
                oilTime_count = beanV3.getOilTime();
                oilLong_count = beanV3.getOilLong();
                luxury_count = beanV3.getLuxury();
                eleTime_count = beanV3.getEleTime();
                eleLong_count = beanV3.getEleLong();
                if (beanV3.getTheme().getTerm_flag() == 1) {
                    snapshot_re.setVisibility(View.VISIBLE);
                } else {
                    snapshot_re.setVisibility(View.GONE);
                }
                activity_tittle_tv.setText(beanV3.getTheme().getTitle());
                theme_id = beanV3.getTheme().getTheme_id();
                theme_tittle = beanV3.getTheme().getTitle();
                initViews();
            }
        });
    }

    private void initViews() {
        if (oilTime_count > 0) {
            oil_short_event_count_tv.setVisibility(View.VISIBLE);
            oil_short_event_count_tv.setText(oilTime_count + "");
        } else {
            oil_short_event_count_tv.setVisibility(View.GONE);
        }

        if (oilLong_count > 0) {
            oil_inday_event_count_tv.setVisibility(View.VISIBLE);
            oil_inday_event_count_tv.setText(oilLong_count + "");
        } else {
            oil_inday_event_count_tv.setVisibility(View.GONE);
        }

        if (eleTime_count > 0) {
            elec_short_event_count_tv.setVisibility(View.VISIBLE);
            elec_short_event_count_tv.setText(eleTime_count + "");
        } else {
            elec_short_event_count_tv.setVisibility(View.GONE);
        }

        if (eleLong_count > 0) {
            elec_inday_event_count_tv.setVisibility(View.VISIBLE);
            elec_inday_event_count_tv.setText(eleLong_count + "");
        } else {
            elec_inday_event_count_tv.setVisibility(View.GONE);
        }

        if (luxury_count > 0) {
            luxury_event_count_tv.setVisibility(View.VISIBLE);
            luxury_event_count_tv.setText(luxury_count + "");
        } else {
            luxury_event_count_tv.setVisibility(View.GONE);
        }

        elec_short_re.setOnClickListener(this);
        elec_inday_re.setOnClickListener(this);
        oil_short_re.setOnClickListener(this);
        oil_inday_re.setOnClickListener(this);
        luxury_re.setOnClickListener(this);
        snapshot_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.elec_short_re:
                if (eleTime_count > 0) {
                    Intent intent01 = new Intent(this, EventListActivity.class);
                    intent01.putExtra("type", 1);
                    startActivity(intent01);
                } else {
                    showToast("暂无活动");
                }
                break;
            case R.id.elec_inday_re:
                if (eleLong_count > 0) {
                    Intent intent02 = new Intent(this, EventListActivity.class);
                    intent02.putExtra("type", 2);
                    startActivity(intent02);
                } else {
                    showToast("暂无活动");
                }
                break;
            case R.id.oil_short_re:
                if (oilTime_count > 0) {
                    Intent intent03 = new Intent(this, EventListActivity.class);
                    intent03.putExtra("type", 3);
                    startActivity(intent03);
                } else {
                    showToast("暂无活动");
                }
                break;
            case R.id.oil_inday_re:
                if (oilLong_count > 0) {
                    Intent intent04 = new Intent(this, EventListActivity.class);
                    intent04.putExtra("type", 4);
                    startActivity(intent04);
                } else {
                    showToast("暂无活动");
                }
                break;
            case R.id.luxury_re:
                if (luxury_count > 0) {
                    Intent intent05 = new Intent(this, EventListActivity.class);
                    intent05.putExtra("type", 5);
                    startActivity(intent05);
                } else {
                    showToast("暂无活动");
                }
                break;
            case R.id.snapshot_re:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent06 = new Intent(EventGuideActivityV3.this, SnapShotActivityV3.class);
                    intent06.putExtra("theme_id", theme_id);
                    intent06.putExtra("theme_tittle", theme_tittle);
                    startActivity(intent06);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), 102);
                }
                break;
            default:
                break;
        }
    }
}
