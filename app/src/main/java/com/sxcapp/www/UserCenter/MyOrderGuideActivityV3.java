package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.sxcapp.www.R;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayOrderListActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.ElecShortOrderListActivityV3;
import com.sxcapp.www.Share.LuxuryShare.LuxuryOrderListActivityV3;
import com.sxcapp.www.Share.OilInDayShare.OilInDayOrderListActivityV3;
import com.sxcapp.www.Share.OilShortShare.OilShortOrderListActivityV3;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenleitao on 2018/5/2.
 */

public class MyOrderGuideActivityV3 extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.lease_re)
    RelativeLayout lease_re;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_guide_v3);
        setStatusView(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("我的订单", null);
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        elec_short_re.setOnClickListener(this);
        elec_inday_re.setOnClickListener(this);
        oil_short_re.setOnClickListener(this);
        oil_inday_re.setOnClickListener(this);
        luxury_re.setOnClickListener(this);
        lease_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.elec_short_re:
                startActivity(new Intent(this, ElecShortOrderListActivityV3.class));
                break;
            case R.id.elec_inday_re:
                startActivity(new Intent(this, ElecInDayOrderListActivityV3.class));
                break;
            case R.id.oil_short_re:
                startActivity(new Intent(this, OilShortOrderListActivityV3.class));
                break;
            case R.id.oil_inday_re:
                startActivity(new Intent(this, OilInDayOrderListActivityV3.class));
                break;
            case R.id.lease_re:
                startActivity(new Intent(this, OrderListActivity.class));
                break;
            case R.id.luxury_re:
                startActivity(new Intent(this, LuxuryOrderListActivityV3.class));
                break;
            default:
                break;
        }

    }
}
