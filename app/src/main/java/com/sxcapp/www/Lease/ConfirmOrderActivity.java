package com.sxcapp.www.Lease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.AliPay.PayResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.DepositInfo;
import com.sxcapp.www.Lease.Bean.PrepayIdBean;
import com.sxcapp.www.Lease.Bean.PrepayObserver;
import com.sxcapp.www.Lease.Bean.PrepayResult;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Bean.AliSign;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.MessageEvent;
import com.sxcapp.www.Util.PayUtil;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;
import com.sxcapp.www.wxapi.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;


/**
 * 支付租金界面
 * Created by wenleitao on 2017/7/24.
 */

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    private String order_no, total_cost;
    private boolean ali_check = true;
    private boolean wx_check = false;
    private boolean balance_check = false;
    private static final int SDK_PAY_FLAG = 1;
    private LeaseService service;
    private UserCenterService userCenterService;
    private IWXAPI api;
    private String car_id, order_id;
    @BindView(R.id.pay_btn)
    Button pay_btn;
    @BindView(R.id.ali_re)
    RelativeLayout ali_re;
    @BindView(R.id.wx_re)
    RelativeLayout wx_re;
    @BindView(R.id.balance_re)
    RelativeLayout banlance_re;
    @BindView(R.id.ali_check_iv)
    ImageView ali_check_iv;
    @BindView(R.id.wx_check_iv)
    ImageView wx_check_iv;
    @BindView(R.id.balance_check_iv)
    ImageView balance_check_iv;
    @BindView(R.id.total_cost_tv)
    TextView total_cost_tv;
    private PayUtil payUtil = new PayUtil();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("支付租金", null);
        order_no = getIntent().getStringExtra("order_no");
        order_id = getIntent().getStringExtra("order_id");
        car_id = getIntent().getStringExtra("car_id");
        total_cost = getIntent().getStringExtra("total_cost");
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        userCenterService = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        initViews();

    }

    private void initViews() {
        total_cost_tv.setText("￥" + total_cost);
        wx_re.setOnClickListener(this);
        ali_re.setOnClickListener(this);
        banlance_re.setOnClickListener(this);
        pay_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_btn:
                if (ali_check) {
                    doALiPay();
                } else if (wx_check) {
                    wxPay();
                } else if (balance_check) {
                    balancePay();
                }
                break;
            case R.id.ali_re:
                if (!ali_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.check);
                    wx_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    balance_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    ali_check = true;
                    wx_check = false;
                    balance_check = false;
                }
                break;
            case R.id.wx_re:
                if (!wx_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    wx_check_iv.setBackgroundResource(R.mipmap.check);
                    balance_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    ali_check = false;
                    wx_check = true;
                    balance_check = false;
                }
                break;
            case R.id.balance_re:
                if (!balance_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    wx_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    balance_check_iv.setBackgroundResource(R.mipmap.check);
                    ali_check = false;
                    wx_check = false;
                    balance_check = true;
                }
                break;
            default:
                break;

        }
    }

    /**
     * 余额支付
     */
    private void balancePay() {
        Observable<Result<Object>> ob = service.balancePay(SharedData.getInstance().getString(SharedData._user_id), order_no);
        ob.compose(compose(this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(this) {

            @Override
            protected void onHandleSuccess(Object o) {
                isToPayDeposit();
            }
        });
    }

    /**
     * 微信支付
     */
    private void wxPay() {
        Observable<PrepayResult<PrepayIdBean>> ob = service.getPrepayId(order_no, total_cost, "租车订单", "order");
        ob.compose(compose(this.<PrepayResult<PrepayIdBean>>bindToLifecycle())).subscribe(new PrepayObserver<PrepayIdBean>(this) {
            @Override
            protected void onHandleSuccess(PrepayIdBean prepayIdBean) {
                PayReq req = new PayReq();
                req.appId = prepayIdBean.getAppid();
                req.partnerId = prepayIdBean.getPartnerid();
                req.prepayId = prepayIdBean.getPrepayid();
                req.nonceStr = prepayIdBean.getNoncestr();
                req.timeStamp = prepayIdBean.getTimestamp();
                req.packageValue = "Sign=WXPay";
                req.sign = prepayIdBean.getSign();
//                        req.extData			= "app data"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        MyApplication.getInstance().gotoMainActivity();
                        isToPayDeposit();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }
                case 55:
                    isToPayDeposit();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 微信支付结果回调
     * 用handler是防止在主线程里操作UI
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("success".equals(messageEvent.getMessage())) {
            showToast("支付成功");
            mHandler.sendEmptyMessage(55);
        } else {
            showToast("支付失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 判断租金支付成功后是否跳转支付押金界面
     * 押金未0则不跳转
     */
    private void isToPayDeposit() {
        LeaseService service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        Observable<Result<DepositInfo>> ob = service.getDepositInfo(SharedData.getInstance().getString(SharedData._user_id), car_id);
        ob.compose(compose(ConfirmOrderActivity.this.<Result<DepositInfo>>bindToLifecycle())).subscribe(new BaseObserver<DepositInfo>(ConfirmOrderActivity.this) {
            @Override
            protected void onHandleSuccess(DepositInfo depositInfo) {
                if ("0.00".equals(depositInfo.getTotal_cost() + "") || "0.0".equals(depositInfo.getTotal_cost() + "") || "0".equals(depositInfo.getTotal_cost() + "")) {
                    Intent intent01 = new Intent(ConfirmOrderActivity.this, LeaseSuccessActivity.class);
                    intent01.putExtra("order_id", order_id);
                    startActivity(intent01);
                } else {
                    Intent intent = new Intent(ConfirmOrderActivity.this, PayDepositActivity.class);
                    intent.putExtra("car_id", car_id);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("order_no", order_no);
                    intent.putExtra("img", getIntent().getStringExtra("img"));
                    intent.putExtra("car_name", getIntent().getStringExtra("car_name"));
                    startActivity(intent);
                }

            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
            }
        });

    }


    private void doALiPay() {
        showProgressDlg();
        pay_btn.setClickable(false);
        Observable<Result<AliSign>> ob = userCenterService.getALiSign(order_no, total_cost, "租车订单", "order");
        ob.compose(compose(this.<Result<AliSign>>bindToLifecycle())).subscribe(new BaseObserver<AliSign>(this) {
            @Override
            protected void onHandleSuccess(AliSign aliSign) {
                removeProgressDlg();
                pay_btn.setClickable(true);
                payUtil.doWebPay(ConfirmOrderActivity.this, mHandler, aliSign.getPayInfo());
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                pay_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                pay_btn.setClickable(true);
            }
        });

    }

}
