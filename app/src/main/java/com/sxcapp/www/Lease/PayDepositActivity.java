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

import com.bumptech.glide.Glide;
import com.sxcapp.www.AliPay.PayResult;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Lease.Bean.DepositInfo;
import com.sxcapp.www.Lease.Bean.DepositNo;
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
import com.sxcapp.www.Util.Properties;
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

import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 支付押金界面
 * Created by wenleitao on 2017/7/26.
 */

public class PayDepositActivity extends BaseActivity implements View.OnClickListener {
    private LeaseService service;
    private UserCenterService userCenterService;
    private boolean ali_check = true;
    private boolean wx_check = false;
    private String car_id;
    private static final int SDK_PAY_FLAG = 1;
    private PayUtil payUtil = new PayUtil();
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
                        showToast("支付成功");
                        Intent intent01 = new Intent(PayDepositActivity.this, LeaseSuccessActivity.class);
                        intent01.putExtra("order_id", order_id);
                        startActivity(intent01);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };
    private IWXAPI api;
    private String deposit_no;
    private String order_no, order_id;
    private double count;
    @BindView(R.id.deposit_price_tv)
    TextView deposit_price_tv;
    @BindView(R.id.ali_re)
    RelativeLayout ali_re;
    @BindView(R.id.wx_re)
    RelativeLayout wx_re;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.ali_check_iv)
    ImageView ali_check_iv;
    @BindView(R.id.wx_check_iv)
    ImageView wx_check_iv;
    @BindView(R.id.pay_btn)
    Button pay_btn;
    @BindView(R.id.car_iv)
    ImageView car_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_deposit);
        ButterKnife.bind(this);
        setTopbarLeftBackBtn();
        setTopBarTitle("车辆押金", null);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        userCenterService = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        car_id = getIntent().getStringExtra("car_id");
        order_no = getIntent().getStringExtra("order_no");
        order_id = getIntent().getStringExtra("order_id");
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        EventBus.getDefault().register(this);
        initViews();
        load();
    }

    private void load() {
        Glide.with(PayDepositActivity.this).load(Properties.baseImageUrl + getIntent().getStringExtra("img")).into(car_iv);
        car_name_tv.setText(getIntent().getStringExtra("car_name"));
        Observable<Result<DepositInfo>> ob = service.getDepositInfo(SharedData.getInstance().getString(SharedData._user_id), car_id);
        ob.compose(compose(this.<Result<DepositInfo>>bindToLifecycle())).subscribe(new BaseObserver<DepositInfo>(this) {
            @Override
            protected void onHandleSuccess(DepositInfo depositInfo) {
                DecimalFormat df = new DecimalFormat("#0.00");
                deposit_price_tv.setText("￥" + df.format(depositInfo.getTotal_cost()));
                count = depositInfo.getTotal_cost();

            }
        });
    }

    private void initViews() {
        pay_btn.setOnClickListener(this);
        wx_re.setOnClickListener(this);
        ali_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ali_re:
                if (!ali_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.check);
                    wx_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    ali_check = true;
                    wx_check = false;
                }
                break;
            case R.id.wx_re:
                if (!wx_check) {
                    ali_check_iv.setBackgroundResource(R.mipmap.pay_uncheck);
                    wx_check_iv.setBackgroundResource(R.mipmap.check);
                    ali_check = false;
                    wx_check = true;
                }
                break;
            case R.id.pay_btn:
                pay_btn.setClickable(false);
                showProgressDlg();
                Observable<Result<DepositNo>> resultOb = service.getDepositNo(order_no);
                resultOb.compose(compose(this.<Result<DepositNo>>bindToLifecycle())).subscribe(new BaseObserver<DepositNo>(this) {
                    @Override
                    protected void onHandleSuccess(DepositNo o) {
                        deposit_no = o.getOrder_no();
                        if (ali_check) {
                            doALiPay();
                        } else if (wx_check) {
                            removeProgressDlg();
                            pay_btn.setClickable(true);
                            wxPay();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private void wxPay() {
        Observable<PrepayResult<PrepayIdBean>> ob = service.getPrepayId(deposit_no, count + "", "车辆押金", "deposit");
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


    private void doALiPay() {

        Observable<Result<AliSign>> ob = userCenterService.getALiSign(deposit_no, count + "", "车辆押金", "deposit");
        ob.compose(compose(this.<Result<AliSign>>bindToLifecycle())).subscribe(new BaseObserver<AliSign>(this) {
            @Override
            protected void onHandleSuccess(AliSign aliSign) {
                removeProgressDlg();
                pay_btn.setClickable(true);
                payUtil.doWebPay(PayDepositActivity.this, mHandler, aliSign.getPayInfo());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        if ("success".equals(messageEvent.getMessage())) {
            Intent intent01 = new Intent(PayDepositActivity.this, LeaseSuccessActivity.class);
            intent01.putExtra("order_id", order_id);
            startActivity(intent01);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
