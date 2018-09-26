package com.sxcapp.www.Share.ShareActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.TTCouponListBean;
import com.sxcapp.www.UserCenter.Bean.CouponRemarkBean;
import com.sxcapp.www.UserCenter.Bean.MyCouponCountBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/12/14.
 */

public class TTLuckyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.view01)
    View view01;
    @BindView(R.id.view02)
    View view02;
    @BindView(R.id.view03)
    View view03;
    @BindView(R.id.view04)
    View view04;
    @BindView(R.id.view05)
    View view05;
    @BindView(R.id.view06)
    View view06;
    @BindView(R.id.view07)
    View view07;
    @BindView(R.id.view08)
    View view08;
    @BindView(R.id.lucky_re)
    RelativeLayout lucky_re;
    @BindView(R.id.frame_lin)
    LinearLayout frame_lin;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.tv04)
    TextView tv04;
    @BindView(R.id.tv06)
    TextView tv06;
    @BindView(R.id.tv08)
    TextView tv08;
    private boolean isFrame01 = true;
    private int index = 1;
    private int count = 1;
    private final int BEGINSIGN = 20;
    private final int FRAME_CHANGE = 21;
    private double random1 = 0;
    private double random3 = 0;
    private double random5 = 0;
    private double random7 = 0;
    private static double rate1 = 0.125;
    private static double rate2 = 0.125;
    private static double rate3 = 0.125;
    private static double rate4 = 0.125;
    private static double rate5 = 0.125;
    private static double rate6 = 0.125;
    private static double rate7 = 0.125;
    private static double rate8 = 0.125;
    private int lucky_number;
    private Dialog adv_dialog;
    private int my_count = 0;
    private UserCenterService service;
    private String store_id;
    private String user_id;
    private String type_id;
    private Map<Integer, String> couponId_map = new HashMap<>();
    private TTCouponListBean data_list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_lotto);
        setTopbarLeftBackBtn();
        setTopBarTitle("抽奖活动", null);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessage(FRAME_CHANGE);
        store_id = getIntent().getStringExtra("store_id");
        type_id = getIntent().getStringExtra("type_id");
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        couponId_map.put(2, "000");
        couponId_map.put(4, "000");
        couponId_map.put(6, "000");
        couponId_map.put(8, "000");
        bindOnclick();
        getCouponRemark();
    }

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case BEGINSIGN:
                turnLucky(index);
                if (count < 25) {
                    mHandler.sendEmptyMessageDelayed(BEGINSIGN, 150);
                } else {
                    if (index == lucky_number) {
                        uploadResult(index);
                    } else {
                        mHandler.sendEmptyMessageDelayed(BEGINSIGN, 450);
                    }
                }
                index++;
                count++;
                if (index == 9) {
                    index = 1;
                }

                break;
            case FRAME_CHANGE:
                if (isFrame01) {
                    frame_lin.setBackgroundResource(R.mipmap.lucky_frame02);
                    isFrame01 = false;
                } else {
                    frame_lin.setBackgroundResource(R.mipmap.lucky_frame);
                    isFrame01 = true;
                }
                mHandler.sendEmptyMessageDelayed(FRAME_CHANGE, 650);
                break;
            default:
                break;

        }
    }

    private void getCouponRemark() {
        Observable<Result<TTCouponListBean>> ob = service.getTTCouponList(store_id, type_id);
        ob.compose(compose(this.<Result<TTCouponListBean>>bindToLifecycle())).subscribe(new BaseObserver<TTCouponListBean>(this) {
            @Override
            protected void onHandleSuccess(TTCouponListBean couponListBean) {
                for (int i = 0; i < couponListBean.getRemark().size(); i++) {
                    TextView tv = new TextView(TTLuckyActivity.this);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tv.setText((i + 1) + "." + couponListBean.getRemark().get(i));
                    tv.setLayoutParams(layoutParams);
                    tv.setPadding(0, 5, 0, 5);
                    tv.setTextColor(getResources().getColor(R.color.orRed));
                    tv.setTextSize(15);
                    rules_lin.addView(tv, i);
                    switch (i) {
                        case 0:
                            couponId_map.put(2, couponListBean.getCouponList().get(i).getCoupon_id());
                            break;
                        case 1:
                            couponId_map.put(4, couponListBean.getCouponList().get(i).getCoupon_id());
                            break;
                        case 2:
                            couponId_map.put(6, couponListBean.getCouponList().get(i).getCoupon_id());
                            break;
                        case 3:
                            couponId_map.put(8, couponListBean.getCouponList().get(i).getCoupon_id());
                            break;
                        default:
                            break;
                    }
                }
                data_list = couponListBean;
                tv02.setText(couponListBean.getCouponList().get(0).getCoupon_title());
                tv04.setText(couponListBean.getCouponList().get(1).getCoupon_title());
                tv06.setText(couponListBean.getCouponList().get(2).getCoupon_title());
                tv08.setText(couponListBean.getCouponList().get(3).getCoupon_title());
            }
        });
    }

    private void bindOnclick() {
        lucky_re.setOnClickListener(this);
    }

    private void getCount() {
        showProgressDlg();
        Observable<Result<MyCouponCountBean>> ob = service.getCustomerCoupon(user_id, store_id);
        ob.compose(compose(this.<Result<MyCouponCountBean>>bindToLifecycle())).subscribe(new BaseObserver<MyCouponCountBean>(this) {
            @Override
            protected void onHandleSuccess(MyCouponCountBean myCouponCountBean) {
                if (myCouponCountBean.getSurplusNum() > 0) {
                    removeProgressDlg();
                    setProbability(myCouponCountBean);
                    lucky_number = PercentageRandom();
                    Logger.e(lucky_number + "");
                    mHandler.sendEmptyMessage(BEGINSIGN);
                } else {
                    showToast("您今日抽奖机会已用完");
                }
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lucky_re.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                lucky_re.setClickable(true);
            }
        });
    }

    private void setProbability(MyCouponCountBean bean) {
        random1 = 0;
        random3 = 0;
        random5 = 0;
        random7 = 0;
        rate2 = 0.125;
        rate4 = 0.125;
        rate6 = 0.125;
        rate8 = 0.125;
        if (bean.getMaxSurplusNum() >= 1) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(2, 0);
            map.put(4, 0);
            map.put(6, 0);
            map.put(8, 0);
            for (int i = 0; i < bean.getCouponList().size(); i++) {
                if (couponId_map.containsValue(bean.getCouponList().get(i).getCoupon_id())) {
                    map.put(getKey(couponId_map, bean.getCouponList().get(i).getCoupon_id()), bean.getCouponList().get(i).getSurplus_num());
                }
            }
            if (map.get(2) < 1) {
                rate2 = 1;
                random1 = 0.125;
            }
            if (map.get(4) < 1) {
                rate4 = 1;
                random3 = 0.125;
            }
            if (map.get(6) < 1) {
                rate6 = 1;
                random5 = 0.125;
            }
            if (map.get(8) < 1) {
                rate8 = 1;
                random7 = 0.125;
            }

        } else {
            rate2 = 1;
            rate4 = 1;
            rate6 = 1;
            rate8 = 1;
            random1 = 0.125;
            random3 = 0.125;
            random5 = 0.125;
            random7 = 0.125;
        }
    }

    //根据map的value获取map的key
    private static int getKey(Map<Integer, String> map, String value) {
        int key = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                key = entry.getKey();
            }
        }
        return key;
    }

    private void uploadResult(final int ind) {
        showProgressDlg();
        String upload_couponId;
        if (ind == 2 || ind == 4 || ind == 6 || ind == 8) {
            upload_couponId = couponId_map.get(ind);
        } else {
            upload_couponId = "000";
        }
        Observable<Result<Object>> ob = service.uploadLuckyResult(user_id, upload_couponId, store_id);
        ob.compose(compose(this.<Result<Object>>bindToLifecycle())).subscribe(new BaseObserver<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                lucky_re.setClickable(true);
                showAdvertisingWindow(ind);
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lucky_re.setClickable(true);
                hideLight();
                index = 0;
                count = 0;
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                lucky_re.setClickable(true);
                hideLight();
                index = 0;
                count = 0;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lucky_re:
                if (!SharedData.getInstance().isLogin()) {
                    startActivityForResult(new Intent(TTLuckyActivity.this, LoginActivity.class), 23);
                } else {
                    lucky_re.setClickable(false);
                    user_id = SharedData.getInstance().getString(SharedData._user_id);
                    getCount();
                }
                break;
            default:
                break;
        }
    }

    private void turnLucky(int index) {
        switch (index) {
            case 1:
                view01.setVisibility(View.VISIBLE);
                view08.setVisibility(View.GONE);
                break;
            case 2:
                view02.setVisibility(View.VISIBLE);
                view01.setVisibility(View.GONE);
                break;
            case 3:
                view03.setVisibility(View.VISIBLE);
                view02.setVisibility(View.GONE);
                break;
            case 4:
                view04.setVisibility(View.VISIBLE);
                view03.setVisibility(View.GONE);
                break;
            case 5:
                view05.setVisibility(View.VISIBLE);
                view04.setVisibility(View.GONE);
                break;
            case 6:
                view06.setVisibility(View.VISIBLE);
                view05.setVisibility(View.GONE);
                break;
            case 7:
                view07.setVisibility(View.VISIBLE);
                view06.setVisibility(View.GONE);
                break;
            case 8:
                view08.setVisibility(View.VISIBLE);
                view07.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void hideLight() {
        view01.setVisibility(View.GONE);
        view02.setVisibility(View.GONE);
        view03.setVisibility(View.GONE);
        view04.setVisibility(View.GONE);
        view05.setVisibility(View.GONE);
        view06.setVisibility(View.GONE);
        view07.setVisibility(View.GONE);
        view08.setVisibility(View.GONE);
        index = 1;
        count = 1;
    }

    private int PercentageRandom() {
        double randomNumber;
        randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber < 0.125 + random1) {
            return 1;
        } else if (randomNumber >= rate2 && randomNumber < (2 * rate2)) {
            return 2;
        } else if (randomNumber >= 0.25 && randomNumber < 0.375 + random3) {
            return 3;
        } else if (randomNumber >= (3 * rate4) && randomNumber < (4 * rate4)) {
            return 4;
        } else if (randomNumber >= 0.5 && randomNumber < 0.625 + random5) {
            return 5;
        } else if (randomNumber >= (5 * rate6) && randomNumber < (6 * rate6)) {
            return 6;
        } else if (randomNumber >= 0.75 && randomNumber < 0.875 + random7) {
            return 7;
        } else if (randomNumber >= (7 * rate8) && randomNumber < (8 * rate8)) {
            return 8;
        }
        return -1;
    }

    public void showAdvertisingWindow(int index) {
        adv_dialog = new Dialog(TTLuckyActivity.this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(TTLuckyActivity.this).inflate(R.layout.tt_lucky_pop, null);
        adv_dialog.setContentView(view);
        final Window dialogWindow = adv_dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        adv_dialog.setCanceledOnTouchOutside(false);
        adv_dialog.setCancelable(false);
        dialogWindow.setWindowAnimations(R.style.dialog_anim_style);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.adv_cancel_iv);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLight();
                adv_dialog.dismiss();
            }
        });
        TextView result_tv = (TextView) view.findViewById(R.id.result_tv);
        switch (index) {
            case 2:
                result_tv.setText(data_list.getCouponList().get(0).getCoupon_title());
                break;
            case 4:
                result_tv.setText(data_list.getCouponList().get(1).getCoupon_title());
                break;
            case 6:
                result_tv.setText(data_list.getCouponList().get(2).getCoupon_title());
                break;
            case 8:
                result_tv.setText(data_list.getCouponList().get(3).getCoupon_title());
                break;
            default:
                result_tv.setText("差一点点");
                break;
        }
        adv_dialog.show();
    }
}
