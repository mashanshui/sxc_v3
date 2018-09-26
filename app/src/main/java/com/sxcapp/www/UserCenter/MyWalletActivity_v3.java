package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.WalletChangeEvent;
import com.sxcapp.www.UserCenter.Bean.WalletInfoBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * V3钱包界面
 * Created by wenleitao on 2018/4/3.
 */

public class MyWalletActivity_v3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.balance_tv)
    TextView balance_tv;
    @BindView(R.id.share_deposit_tv)
    TextView share_deposit_tv;
    @BindView(R.id.integral_tv)
    TextView integral_tv;
    @BindView(R.id.coupon_tv)
    TextView coupon_tv;
    @BindView(R.id.useful_coupon_tv)
    TextView useful_coupon_tv;
    @BindView(R.id.invalid_coupon_tv)
    TextView invalid_coupon_tv;
    @BindView(R.id.lease_deposit_tv)
    TextView lease_deposit_tv;
    //    充值优惠
    @BindView(R.id.recharge_discount_lin)
    LinearLayout recharge_discount_lin;
    @BindView(R.id.share_deposit_state_lin)
    LinearLayout share_deposit_state_lin;
    @BindView(R.id.lease_deposit_state_lin)
    LinearLayout lease_deposit_state_lin;
    @BindView(R.id.balance_re)
    RelativeLayout balance_re;
    @BindView(R.id.share_deposit_re)
    RelativeLayout share_deposit_re;
    @BindView(R.id.coupon_re)
    RelativeLayout coupon_re;
    @BindView(R.id.useful_coupon_re)
    RelativeLayout useful_coupon_re;
    @BindView(R.id.invalid_coupon_re)
    RelativeLayout invalid_coupon_re;
    @BindView(R.id.integral_shop_re)
    RelativeLayout integral_shop_re;
    @BindView(R.id.about_integral_tv)
    TextView about_integral_tv;
    @BindView(R.id.integral_shop_tv)
    TextView integral_shop_tv;
    @BindView(R.id.lease_deposit_re)
    RelativeLayout lease_deposit_re;
    private String integral_shop_url;
    private String integral_shop_tittle;
    private String about_integral_url;
    private String about_integral_tittle;


    private UserCenterService service;
    private String user_id;
    private int ins_height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_v3);
        setTopbarLeftWhiteBackBtn();
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("我的钱包", null);
        ButterKnife.bind(this);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        ins_height = AndroidTool.dip2px(this, 51);
        EventBus.getDefault().register(this);
        loadData();
        bindOnClick();
    }

    private void bindOnClick() {
        balance_re.setOnClickListener(this);
        share_deposit_re.setOnClickListener(this);
        coupon_re.setOnClickListener(this);
        useful_coupon_re.setOnClickListener(this);
        invalid_coupon_re.setOnClickListener(this);
        integral_shop_re.setOnClickListener(this);
        about_integral_tv.setOnClickListener(this);
        lease_deposit_re.setOnClickListener(this);

    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<WalletInfoBeanV3>> ob = service.getWalletInfoV3(user_id);
        ob.compose(compose(this.<CodeResultV3<WalletInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<WalletInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(WalletInfoBeanV3 walletInfoBeanV3) {
                removeProgressDlg();
                balance_tv.setText(walletInfoBeanV3.getCustomer_balance() + "元");
                share_deposit_tv.setText(walletInfoBeanV3.getCustomer_share_deposit() + "元");
                integral_tv.setText(walletInfoBeanV3.getCustomer_integral());
                coupon_tv.setText(walletInfoBeanV3.getCustomer_coupontotal() + "张");
                useful_coupon_tv.setText(walletInfoBeanV3.getCustomer_canuse_coupontotal() + "张");
                invalid_coupon_tv.setText(walletInfoBeanV3.getCustomer_cantuse_coupontotal() + "张");
                lease_deposit_tv.setText(walletInfoBeanV3.getCustomer_sxc_deposit() + "元");
                for (int i = 0; i < walletInfoBeanV3.getCustomer_balance_remark().size(); i++) {
                    String str = walletInfoBeanV3.getCustomer_balance_remark().get(i);
                    TextView tv = new TextView(MyWalletActivity_v3.this);
                    RelativeLayout re = new RelativeLayout(MyWalletActivity_v3.this);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ins_height);
                    RelativeLayout.LayoutParams layoutParams_tv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ins_height);
                    layoutParams_tv.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    re.setLayoutParams(layoutParams);
                    tv.setText(str);
                    tv.setLayoutParams(layoutParams_tv);
                    tv.setTextSize(11);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setTextColor(getResources().getColor(R.color.black_tv_52));
                    re.addView(tv);
                    recharge_discount_lin.addView(re);
                    if (i < (walletInfoBeanV3.getCustomer_balance_remark().size() - 1)) {
                        View view = new View(MyWalletActivity_v3.this);
                        ViewGroup.LayoutParams layoutParams_view = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        view.setBackgroundColor(getResources().getColor(R.color.lightGray2));
                        view.setLayoutParams(layoutParams_view);
                        recharge_discount_lin.addView(view);
                    }
                }
                for (int i = 0; i < walletInfoBeanV3.getCustomer_share_deposit_remark().size(); i++) {
                    String str = walletInfoBeanV3.getCustomer_share_deposit_remark().get(i);
                    TextView tv = new TextView(MyWalletActivity_v3.this);
                    RelativeLayout re = new RelativeLayout(MyWalletActivity_v3.this);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ins_height);
                    RelativeLayout.LayoutParams layoutParams_tv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ins_height);
                    layoutParams_tv.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    re.setLayoutParams(layoutParams);
                    tv.setText(str);
                    tv.setLayoutParams(layoutParams_tv);
                    tv.setTextSize(11);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setTextColor(getResources().getColor(R.color.black_tv_52));
                    re.addView(tv);
                    share_deposit_state_lin.addView(re);
                    if (i < (walletInfoBeanV3.getCustomer_share_deposit_remark().size() - 1)) {
                        View view = new View(MyWalletActivity_v3.this);
                        ViewGroup.LayoutParams layoutParams_view = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        view.setBackgroundColor(getResources().getColor(R.color.lightGray2));
                        view.setLayoutParams(layoutParams_view);
                        share_deposit_state_lin.addView(view);
                    }
                }
                for (int i = 0; i < walletInfoBeanV3.getCustomer_sxc_deposit_remark().size(); i++) {
                    String str = walletInfoBeanV3.getCustomer_sxc_deposit_remark().get(i);
                    TextView tv = new TextView(MyWalletActivity_v3.this);
                    RelativeLayout re = new RelativeLayout(MyWalletActivity_v3.this);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ins_height);
                    RelativeLayout.LayoutParams layoutParams_tv = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ins_height);
                    layoutParams_tv.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    re.setLayoutParams(layoutParams);
                    tv.setText(str);
                    tv.setLayoutParams(layoutParams_tv);
                    tv.setTextSize(11);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setTextColor(getResources().getColor(R.color.black_tv_52));
                    re.addView(tv);
                    lease_deposit_state_lin.addView(re);
                    if (i < (walletInfoBeanV3.getCustomer_sxc_deposit_remark().size() - 1)) {
                        View view = new View(MyWalletActivity_v3.this);
                        ViewGroup.LayoutParams layoutParams_view = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        view.setBackgroundColor(getResources().getColor(R.color.lightGray2));
                        view.setLayoutParams(layoutParams_view);
                        lease_deposit_state_lin.addView(view);
                    }
                }
                List<String> strList = walletInfoBeanV3.getCustomer_integral_remark();
                integral_shop_tv.setText(strList.get(0).substring(0, strList.get(0).indexOf("_")));
                integral_shop_url = strList.get(0).substring(strList.get(0).indexOf("_") + 1, strList.get(0).length());
                integral_shop_tittle = strList.get(0).substring(0, strList.get(0).indexOf("_"));
                about_integral_url = strList.get(0).substring(strList.get(0).indexOf("_") + 1, strList.get(0).length());
                about_integral_tittle = strList.get(0).substring(0, strList.get(0).indexOf("_"));

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance_re:
                startActivity(new Intent(this, BalanceActivity_V3.class));
                break;
            case R.id.share_deposit_re:
                startActivity(new Intent(this, ShareDepositActivity_V3.class));
                break;
            case R.id.coupon_re:
                startActivity(new Intent(this, MyCouponActivity_V3.class));
                break;
            case R.id.lease_deposit_re:
                startActivity(new Intent(this, LeaseDepositActivity_V3.class));
                break;
            case R.id.useful_coupon_re:
                startActivity(new Intent(this, MyCouponActivity_V3.class));
                break;
            case R.id.invalid_coupon_re:
                Intent intent = new Intent(this, MyCouponActivity_V3.class);
                intent.putExtra("type", "invalid");
                startActivity(intent);
                break;
            case R.id.integral_shop_re:
                openWebView(integral_shop_url + "?userId=" + SharedData.getInstance().getString(SharedData._user_id), integral_shop_tittle, true);
                break;
            case R.id.about_integral_tv:
                openWebView(about_integral_url, about_integral_tittle, true);
                break;
            default:
                break;


        }
    }

    /**
     * 微信支付结果回调
     * 用handler是防止在主线程里操作UI
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(WalletChangeEvent event) {
        if ("share_deposit".equals(event.getType())) {
            share_deposit_tv.setText(event.getCount() + "元");
        } else if ("balance".equals(event.getType())) {
            balance_tv.setText(event.getCount() + "元");
        } else if ("lease_deposit".equals(event.getType())) {
            lease_deposit_tv.setText(event.getCount() + "元");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
