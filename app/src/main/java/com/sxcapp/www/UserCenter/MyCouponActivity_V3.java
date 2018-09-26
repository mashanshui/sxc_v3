package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.CouponFrgAdapter;
import com.sxcapp.www.UserCenter.Bean.CouponBeanV3;
import com.sxcapp.www.UserCenter.Bean.MyCouponListBeanv3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/4.
 */

public class MyCouponActivity_V3 extends BaseActivity {
    @BindView(R.id.useful_coupon_re)
    RelativeLayout useful_coupon_re;
    @BindView(R.id.invalid_coupon_re)
    RelativeLayout invalid_coupon_re;
    @BindView(R.id.useful_coupon_tv)
    TextView useful_coupon_tv;
    @BindView(R.id.useful_coupon_view)
    View useful_coupon_view;
    @BindView(R.id.invalid_coupon_tv)
    TextView invalid_coupon_tv;
    @BindView(R.id.invalid_coupon_view)
    View invalid_coupon_view;
    @BindView(R.id.coupon_vp)
    ViewPager coupon_vp;
    private UserCenterService service;
    private String user_id;
    private List<CouponBeanV3> useful_list = new ArrayList<>();
    private List<CouponBeanV3> invaild_list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycoupon_v3);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("优惠券", null);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        loadData();
        useful_coupon_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupon_vp.setCurrentItem(0);
            }
        });
        invalid_coupon_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupon_vp.setCurrentItem(1);
            }
        });

    }

    private void loadData() {
        Observable<CodeResultV3<MyCouponListBeanv3>> ob = service.getMyCouponV3(user_id);
        ob.compose(compose(this.<CodeResultV3<MyCouponListBeanv3>>bindToLifecycle())).subscribe(new CodeObserverV3<MyCouponListBeanv3>(this) {
            @Override
            protected void onHandleSuccess(MyCouponListBeanv3 beanv3) {
                List<CouponBeanV3> couponBeanV3List = beanv3.getList();
                if (couponBeanV3List.size() > 0) {
                    for (int i = 0; i < couponBeanV3List.size(); i++) {


                        if ("1".equals(couponBeanV3List.get(i).getCoupon_isuse())) {
                            invaild_list.add(couponBeanV3List.get(i));
                        } else {
                            if ("0".equals(couponBeanV3List.get(i).getCoupon_isouttime())) {
                                useful_list.add(couponBeanV3List.get(i));
                            } else {
                                invaild_list.add(couponBeanV3List.get(i));
                            }
                        }
                    }
                }
                UsefulCouponListFragment fragment = UsefulCouponListFragment.NewInstance((ArrayList<CouponBeanV3>) useful_list);
                InvalidCouponListFragment fragment02 = InvalidCouponListFragment.NewInstance((ArrayList<CouponBeanV3>) invaild_list);
                fragmentList.add(fragment);
                fragmentList.add(fragment02);
                useful_coupon_tv.setText("可用券("+useful_list.size()+")");
                invalid_coupon_tv.setText("失效券("+invaild_list.size()+")");
                CouponFrgAdapter adapter = new CouponFrgAdapter(MyCouponActivity_V3.this.getSupportFragmentManager(), fragmentList);
                coupon_vp.setAdapter(adapter);
                coupon_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 0) {
                            useful_coupon_tv.setTextColor(MyCouponActivity_V3.this.getResources().getColor(R.color.top_bar_red));
                            useful_coupon_view.setBackgroundColor(MyCouponActivity_V3.this.getResources().getColor(R.color.top_bar_red));
                            invalid_coupon_tv.setTextColor(MyCouponActivity_V3.this.getResources().getColor(R.color.black_tv_52));
                            invalid_coupon_view.setBackgroundColor(MyCouponActivity_V3.this.getResources().getColor(R.color.black_tv_52));
                        } else {
                            useful_coupon_tv.setTextColor(MyCouponActivity_V3.this.getResources().getColor(R.color.black_tv_52));
                            useful_coupon_view.setBackgroundColor(MyCouponActivity_V3.this.getResources().getColor(R.color.black_tv_52));
                            invalid_coupon_tv.setTextColor(MyCouponActivity_V3.this.getResources().getColor(R.color.top_bar_red));
                            invalid_coupon_view.setBackgroundColor(MyCouponActivity_V3.this.getResources().getColor(R.color.top_bar_red));
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                if (getIntent().hasExtra("type")) {
                    coupon_vp.setCurrentItem(1);
                }
            }
        });
    }
}
