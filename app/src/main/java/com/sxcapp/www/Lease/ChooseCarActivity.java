package com.sxcapp.www.Lease;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.LeaseCar;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 按车型分类界面
 * Created by wenleitao on 2017/7/12.
 */

public class ChooseCarActivity extends BaseActivity {
    private List<CarListFragment> fragmentList;
    @BindView(R.id.tab_indicator)
    TabLayout tab_indicator;
    @BindView(R.id.car_viewpager)
    ViewPager vp;
    private TextView fifter_tv;
    private CarListFragment all_frg, suv_frg, elec_frg, per_frg, expen_frg, cheap_frg, busi_frg, hand_frg;
    private Bundle bundle;
    private CarFrgAdapter adapter;
    private LeaseService service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecar_type);
        ButterKnife.bind(this);
        fragmentList = new ArrayList<>();
        setTopbarLeftBackBtn();
        setTopBarTitle("选择车型", null);
        bundle = getIntent().getExtras();
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        initViews();
    }

    private void initViews() {

//        fifter_tv = (TextView) findViewById(R.id.filter_tv);
//        fifter_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("筛选");
//            }
//        });

        all_frg = CarListFragment.newInstance();
        suv_frg = CarListFragment.newInstance();
        elec_frg = CarListFragment.newInstance();
        per_frg = CarListFragment.newInstance();
        expen_frg = CarListFragment.newInstance();
        cheap_frg = CarListFragment.newInstance();
        busi_frg = CarListFragment.newInstance();
        hand_frg = CarListFragment.newInstance();
        Bundle bundle1 = (Bundle) bundle.clone();
        bundle1.putString("name", "全部");

        Bundle bundle2 = (Bundle) bundle.clone();
        bundle2.putString("name", "SUV");

        Bundle bundle3 = (Bundle) bundle.clone();
        bundle3.putString("name", "电动汽车型");

        Bundle bundle4 = (Bundle) bundle.clone();
        bundle4.putString("name", "个性车型");

        Bundle bundle5 = (Bundle) bundle.clone();
        bundle5.putString("name", "豪华型");

        Bundle bundle6 = (Bundle) bundle.clone();
        bundle6.putString("name", "经济型");

        Bundle bundle7 = (Bundle) bundle.clone();
        bundle7.putString("name", "商务型");

        Bundle bundle8 = (Bundle) bundle.clone();
        bundle8.putString("name", "手动紧凑型");

        all_frg.setArguments(bundle1);
        suv_frg.setArguments(bundle2);
        elec_frg.setArguments(bundle3);
        per_frg.setArguments(bundle4);
        expen_frg.setArguments(bundle5);
        cheap_frg.setArguments(bundle6);
        busi_frg.setArguments(bundle7);
        hand_frg.setArguments(bundle8);

        fragmentList.add(all_frg);
        fragmentList.add(suv_frg);
        fragmentList.add(elec_frg);
        fragmentList.add(per_frg);
        fragmentList.add(expen_frg);
        fragmentList.add(cheap_frg);
        fragmentList.add(busi_frg);
        fragmentList.add(hand_frg);
        adapter = new CarFrgAdapter(this.getSupportFragmentManager(), fragmentList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(adapter);
        tab_indicator.setupWithViewPager(vp);
        tab_indicator.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        tab_indicator.getTabAt(0).setText("全部");
        tab_indicator.getTabAt(1).setText("SUV");
        tab_indicator.getTabAt(2).setText("电动汽车型");
        tab_indicator.getTabAt(3).setText("个性车型");
        tab_indicator.getTabAt(4).setText("豪华型");
        tab_indicator.getTabAt(5).setText("经济型");
        tab_indicator.getTabAt(6).setText("商务型");
        tab_indicator.getTabAt(7).setText("手动紧凑型");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOGIN_REQUEST) {
                adapter.getCurrentFragment().loginRefresh();
            }
        }
    }

    @Override
    public void isToLogin() {
        super.isToLogin();
    }

    /**
     * @param car  车辆详情实体类
     * @param lease_day 租赁天数
     * @param store_id 门店id
     * @param store_name  门店名称
     * @param picker_time  租车时间
     * @param g_time      还车时间
     * 判断能否租车 可以则跳转到车辆详情 （可能有未处理订单）
     */
    public void isAbleLease(final LeaseCar car, final String lease_day,
                            final String store_id, final String store_name,
                            final String picker_time, final String g_time) {
        showProgressDlg();
        Observable<Result<Object>> ob = service.checkCanLease(SharedData.getInstance().getString(SharedData._user_id));
        ob.compose(compose(this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                SharedData.getInstance().set("is_able_lease", true);
                goToDetail(car, lease_day,
                        store_id, store_name, picker_time, g_time);
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                removeProgressDlg();

            }
        });
    }

    //跳转到车辆详情
    public void goToDetail(final LeaseCar car, final String lease_day,
                           final String store_id, final String store_name,
                           final String picker_time, final String g_time) {
        Intent intent = new Intent(this, OrderInfoActivity.class);
        intent.putExtra("car_bean", car);
        intent.putExtra("lease_day", lease_day);
        intent.putExtra("store_id", store_id);
        intent.putExtra("store_name", store_name);
        intent.putExtra("picker_time", picker_time);
        intent.putExtra("g_time", g_time);
        startActivity(intent);
    }


}
