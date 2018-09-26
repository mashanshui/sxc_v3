package com.sxcapp.www.Share.LuxuryShare;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryAppointCarImagePageAdapterV3;
import com.sxcapp.www.Share.Bean.ConfirmDayTypeCarInfoBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/2.
 */

public class LuxuryConfirmDayTypeActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.car_vp)
    ViewPager car_vp;
    @BindView(R.id.car_name_tv)
    TextView car_name_tv;
    @BindView(R.id.license_num_tv)
    TextView license_num_tv;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.rent_day_type_re)
    RelativeLayout rent_day_type_re;
    @BindView(R.id.rent_day_type_tv)
    TextView rent_day_type_tv;
    @BindView(R.id.rules_lin)
    LinearLayout rules_lin;
    @BindView(R.id.check_audit_btn)
    Button check_audit_btn;
    @BindView(R.id.confirm_btn)
    Button confirm_btn;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;
    private ShareService service;
    private String order_no, rent_days_id;
    private LuxuryAppointCarImagePageAdapterV3 adapterV3;
    private OptionsPickerView pvCustomOptions;
    private int chose_position;
    private List<String> day_type_list, id_list;
    private int time_limit;
    private long netNowDate, order_date;
    private static final int CHANGE_COUNT_DOWN = 11;
    private static final int GET_AUDIT_STATE = 12;
    private Boolean COUNT_DOWN_GOING = true, is_auditing = true;
    private int raise_time;

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case CHANGE_COUNT_DOWN:
                if (COUNT_DOWN_GOING) {
                    if (time_limit == 0) {
                        check_audit_btn.setText("查询审核");
                        check_audit_btn.setTextColor(getResources().getColor(R.color.white));
                        confirm_btn.setTextColor(getResources().getColor(R.color.white));
                        is_auditing = false;
                    } else {
                        raise_time = raise_time - 1;
                        if (raise_time > 0) {
                            check_audit_btn.setText("查询审核(" + raise_time + "s)");
                            mHandler.sendEmptyMessageDelayed(CHANGE_COUNT_DOWN, 1000);
                        } else {
                            check_audit_btn.setText("查询审核");
                            check_audit_btn.setTextColor(getResources().getColor(R.color.white));
                            confirm_btn.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                } else {
                    check_audit_btn.setText("查询审核");
                    check_audit_btn.setTextColor(getResources().getColor(R.color.white));
                    confirm_btn.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case GET_AUDIT_STATE:
                if (is_auditing) {
                    getAuditState();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_confirm_day_type_v3);
        setStatusView(R.color.luxury);
        setTopBarColor(R.color.luxury);
        setTopBarTitle("确认租车时长", null);
        setTopbarLeftWhiteToMainBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        order_no = getIntent().getStringExtra("order_no");
        day_type_list = new ArrayList<>();
        id_list = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<ConfirmDayTypeCarInfoBeanV3>> ob = service.getLuxuryConfirmDayTypeCarInfoV3(order_no);
        ob.compose(compose(this.<CodeResultV3<ConfirmDayTypeCarInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ConfirmDayTypeCarInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final ConfirmDayTypeCarInfoBeanV3 beanV3) {
                removeProgressDlg();
                adapterV3 = new LuxuryAppointCarImagePageAdapterV3(LuxuryConfirmDayTypeActivityV3.this, beanV3.getCar_image());
                car_vp.setAdapter(adapterV3);
                mXcircleindicator.setCount(beanV3.getCar_image().size());
                mXcircleindicator.setSelection(0);
                mXcircleindicator.setRadius(5);
                car_name_tv.setText(beanV3.getCar_name());
                license_num_tv.setText(beanV3.getLicense_plate_number());
                fetch_store_name_tv.setText(beanV3.getFetch_store_name());
                g_store_name_tv.setText(beanV3.getReturn_store_name());
                rent_day_type_re.setOnClickListener(LuxuryConfirmDayTypeActivityV3.this);
                for (int i = 0; i < beanV3.getRent_days_settings().size(); i++) {
                    day_type_list.add(beanV3.getRent_days_settings().get(i).getRent_days_title());
                    id_list.add(beanV3.getRent_days_settings().get(i).getRent_days_id());
                }
                for (int i = 0; i < beanV3.getSettings().size(); i++) {
                    TextView appointTime_tv = new TextView(LuxuryConfirmDayTypeActivityV3.this);
                    appointTime_tv.setText((i + 1) + "." + beanV3.getSettings().get(i));
                    appointTime_tv.setTextColor(Color.parseColor("#c4000000"));
                    appointTime_tv.setTextSize(13);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    appointTime_tv.setLayoutParams(params);
                    appointTime_tv.setPadding(AndroidTool.dip2px(LuxuryConfirmDayTypeActivityV3.this, 12), AndroidTool.dip2px(LuxuryConfirmDayTypeActivityV3.this, 12), 0, 0);
                    rules_lin.addView(appointTime_tv, i);
                }

                time_limit = beanV3.getSurp_audit_time();
                raise_time = time_limit * 60;
                mHandler.sendEmptyMessage(GET_AUDIT_STATE);
                mHandler.sendEmptyMessage(CHANGE_COUNT_DOWN);
                check_audit_btn.setOnClickListener(LuxuryConfirmDayTypeActivityV3.this);
                confirm_btn.setOnClickListener(LuxuryConfirmDayTypeActivityV3.this);
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryConfirmDayTypeActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LuxuryConfirmDayTypeActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "luxury");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_day_type_re:
                initCustomOptionPicker();
                break;
            case R.id.check_audit_btn:
                if (!is_auditing) {
                    showProgressDlg();
                    check_audit_btn.setClickable(false);
                    getAuditState();
                } else {
                    showToast("资料审核中");
                }
                break;
            case R.id.confirm_btn:
                if (!is_auditing) {
                    confirm_btn.setClickable(false);
                    confirmDayType();
                } else {
                    showToast("资料审核中");
                }
                break;
            default:
                break;
        }

    }

    private void confirmDayType() {
        showProgressDlg();
        confirm_btn.setClickable(false);
        rent_day_type_re.setClickable(false);
        Observable<CodeResultV3<Object>> ob = service.confirmLuxuryDayTypeV3(order_no, rent_days_id);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                confirm_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
                Intent intent = new Intent(LuxuryConfirmDayTypeActivityV3.this, LuxuryRentCarActivityV3.class);
                intent.putExtra("order_no", order_no);
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                confirm_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                confirm_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
            }
        });
    }


    private void getAuditState() {
        Observable<CodeResultV3<Object>> ob = service.luxuryCheckGuarantorV3(order_no);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("审核通过");
                check_audit_btn.setClickable(true);
                is_auditing = false;
                COUNT_DOWN_GOING = false;
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                if ("729".equals(value.getCode()) || "730".equals(value.getCode()) || "731".equals(value.getCode())) {
                    showLuxuryAlertDlg(value.getMsg(), R.string.recommit, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeAlertDlg();
                            Intent intent_cons = new Intent(LuxuryConfirmDayTypeActivityV3.this, LuxuryPreAuditActivityV3.class);
                            intent_cons.putExtra("order_no", order_no);
                            startActivity(intent_cons);
                        }
                    }, R.string.contact_service, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000077000"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }, false);
                } else {
                    if (!is_auditing) {
                        removeProgressDlg();
                        showToast(msg);
                    } else {
                        mHandler.sendEmptyMessageDelayed(GET_AUDIT_STATE, 5000);
                    }
                    check_audit_btn.setClickable(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                check_audit_btn.setClickable(true);
            }
        });

    }


    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         *
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                chose_position = options1;

            }
        })
                .setLayoutRes(R.layout.navigation_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView cancel_tv = (TextView) v.findViewById(R.id.cancel_tv);
                        final TextView ok_tv = (TextView) v.findViewById(R.id.confirm_tv);

                        cancel_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        ok_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                rent_day_type_tv.setText(day_type_list.get(chose_position));
                                pvCustomOptions.dismiss();
                                rent_days_id = id_list.get(chose_position);
                            }
                        });


                    }
                }).setContentTextSize(15)
                .isDialog(false)
                .setLineSpacingMultiplier(2.5f)
                .build();

        //添加数据
        pvCustomOptions.setPicker(day_type_list);
        pvCustomOptions.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
