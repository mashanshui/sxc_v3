package com.sxcapp.www.Share.OilShortShare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.security.rp.RPSDK;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.CustomViewPager;
import com.sxcapp.www.CustomerView.DynamicHeightViewPager;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.OilShortCarAdapterV3;
import com.sxcapp.www.Share.Adapter.RuleAdapter;
import com.sxcapp.www.Share.Bean.OilShortAppointBeanV3;
import com.sxcapp.www.Share.Bean.OilShortAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.OilShortCarInfoBeanV3;
import com.sxcapp.www.Share.Bean.RPTokenV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayRentActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.RepairActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/18.
 */

public class OilShareAppointActivityV3 extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.car_vp)
    CustomViewPager car_vp;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView mXcircleindicator;
    @BindView(R.id.rules_vp)
    DynamicHeightViewPager rules_vp;
    @BindView(R.id.fetch_store_name_tv)
    TextView fetch_store_name_tv;
    @BindView(R.id.g_store_name_tv)
    TextView g_store_name_tv;
    @BindView(R.id.no_deductible_cost_tv)
    TextView no_deductible_cost_tv;
    @BindView(R.id.no_deductible_re)
    RelativeLayout no_deductible_re;
    @BindView(R.id.appoint_btn)
    Button appoint_btn;
    @BindView(R.id.check_iv)
    ImageView check_iv;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;


    private ShareService service;
    private String fetch_store_id, g_store_id, fetch_store_name, g_store_name, order_no, car_id, user_id;
    private List<OilShortCarInfoBeanV3> carInfoBeanV3List;
    private OilShortCarAdapterV3 adapter;
    private List<List<String>> ll;
    private RuleAdapter ruleAdapter;
    private int no_deductible = 1;
    private boolean is_check = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_short_appoint_v3);
        setStatusView(R.color.top_bar_red);
        ButterKnife.bind(this);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarColor(R.color.top_bar_red);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("预约用车", null);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        fetch_store_id = getIntent().getStringExtra("fetch_store_id");
        g_store_id = getIntent().getStringExtra("g_store_id");
        fetch_store_name = getIntent().getStringExtra("fetch_store_name");
        g_store_name = getIntent().getStringExtra("g_store_name");
        ll = new ArrayList<>();
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        fetch_store_name_tv.setText(fetch_store_name);
        g_store_name_tv.setText(g_store_name);
        Observable<CodeResultV3<OilShortAppointBeanV3>> ob = service.getCarList_oil_shortV3(fetch_store_id, g_store_id);
        ob.compose(compose(this.<CodeResultV3<OilShortAppointBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OilShortAppointBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final OilShortAppointBeanV3 oilShortAppointBeanV3) {
                removeProgressDlg();
                carInfoBeanV3List = oilShortAppointBeanV3.getList();
                if (carInfoBeanV3List.size() > 0) {
                    adapter = new OilShortCarAdapterV3(OilShareAppointActivityV3.this, carInfoBeanV3List);
                    car_vp.setAdapter(adapter);
                    no_deductible_cost_tv.setText(carInfoBeanV3List.get(0).getNo_deductible() + "元");
                    if (carInfoBeanV3List.size() > 0) {
                        mXcircleindicator.setCount(carInfoBeanV3List.size()); // specify total count of indicators
                        mXcircleindicator.setSelection(0);
                        mXcircleindicator.setRadius(5);
                    }
                    car_id = carInfoBeanV3List.get(0).getSource_id();
                    if (oilShortAppointBeanV3.getList().size() > 0) {
                        for (int i = 0; i < oilShortAppointBeanV3.getList().size(); i++) {
                            ll.add(oilShortAppointBeanV3.getList().get(i).getSettings());
                        }
                        ruleAdapter = new RuleAdapter(OilShareAppointActivityV3.this, ll);
                        HashMap<Integer, View> mChildrenViews = new HashMap<Integer, View>();
                        mChildrenViews = ruleAdapter.getmChildrenViews();
                        rules_vp.setmChildrenViews(mChildrenViews);
                        rules_vp.setAdapter(ruleAdapter);
                        rules_vp.resetHeight(0);
                    }

                    car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            mXcircleindicator.setSelection(position);
                            rules_vp.setCurrentItem(position);
                            car_id = carInfoBeanV3List.get(position).getSource_id();
                            no_deductible_cost_tv.setText(carInfoBeanV3List.get(position).getNo_deductible() + "元");
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    fetch_store_re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OilShareAppointActivityV3.this, StoreDetailActivityV3.class);
                            intent.putExtra("from", "oil");
                            intent.putExtra("store_id", oilShortAppointBeanV3.getFetch_store_id());
                            startActivity(intent);
                        }
                    });
                    select_g_store_re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OilShareAppointActivityV3.this, StoreDetailActivityV3.class);
                            intent.putExtra("from", "oil");
                            intent.putExtra("store_id", oilShortAppointBeanV3.getReturn_store_id());
                            startActivity(intent);
                        }
                    });

                    no_deductible_re.setOnClickListener(OilShareAppointActivityV3.this);
                    appoint_btn.setOnClickListener(OilShareAppointActivityV3.this);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_deductible_re:
                if (is_check) {
                    is_check = false;
                    no_deductible = 0;
                    check_iv.setBackgroundResource(R.mipmap.green_checkbox_uncheck_v3);
                } else {
                    is_check = true;
                    no_deductible = 1;
                    check_iv.setBackgroundResource(R.mipmap.red_checkbox_checked_v3);
                }
                break;
            case R.id.appoint_btn:
                appoint_btn.setClickable(false);
                doCheckUser();
                break;
            default:
                break;
        }

    }

    private void appointCar() {
        Observable<CodeResultV3<OilShortAppointReturnBeanV3>> ob = service.oilShort_appointV3(user_id, fetch_store_id, g_store_id, car_id, no_deductible);
        ob.compose(compose(this.<CodeResultV3<OilShortAppointReturnBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OilShortAppointReturnBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OilShortAppointReturnBeanV3 oilShortAppointReturnBeanV3) {
                removeProgressDlg();
                appoint_btn.setClickable(true);
                Intent intent = new Intent(OilShareAppointActivityV3.this, OilBeginUseCarActivityV3.class);
                intent.putExtra("order_no", oilShortAppointReturnBeanV3.getOrder_no());
                intent.putExtra("order_state", "0");
                startActivity(intent);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OilShortAppointReturnBeanV3> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
            }
        });
    }

    private void doCheckUser() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = service.commonDoCheckUser(user_id, 3, 0 + "", no_deductible, car_id, 0 + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object object) {
//                doRp();
                appoint_btn.setClickable(false);
                appointCar();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                if ("712".equals(value.getCode()) || "714".equals(value.getCode()) || "718".equals(value.getCode()) ||
                        "720".equals(value.getCode()) || "722".equals(value.getCode())) {
                    try {
                        JSONObject jsonObject = new JSONObject(value.getObj().toString());
                        int add_type = jsonObject.getInt("add_type");
                        String order_no = jsonObject.getString("order_no");
                        String add_cost = jsonObject.getString("add_cost");
                        Intent intent = new Intent(OilShareAppointActivityV3.this, RepairActivityV3.class);
                        intent.putExtra("add_cost", add_cost);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("add_type", add_type);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
            }
        });

    }

    private void doRp() {
        Observable<CodeResultV3<RPTokenV3>> ob = service.getRPTokenV3(user_id);
        ob.compose(compose(this.<CodeResultV3<RPTokenV3>>bindToLifecycle())).subscribe(new CodeObserverV3<RPTokenV3>(this) {
            @Override
            protected void onHandleSuccess(RPTokenV3 rpTokenV3) {
                removeProgressDlg();
                RPSDK.start(rpTokenV3.getToken(), OilShareAppointActivityV3.this,
                        new RPSDK.RPCompletedListener() {
                            @Override
                            public void onAuditResult(RPSDK.AUDIT audit) {
                                if (audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
                                    showProgressDlg();
                                    appoint_btn.setClickable(false);
                                    appointCar();
                                } else if (audit == RPSDK.AUDIT.AUDIT_FAIL) { //认证不通过
                                    appoint_btn.setClickable(true);
                                } else if (audit == RPSDK.AUDIT.AUDIT_IN_AUDIT) { //认证中，通常不会出现，只有在认证审核系统内部出现超时，未在限定时间内返回认证结果时出现。此时提示用户系统处理中，稍后查看认证结果即可。
                                } else if (audit == RPSDK.AUDIT.AUDIT_NOT) { //未认证，用户取消
                                    appoint_btn.setClickable(true);
                                } else if (audit == RPSDK.AUDIT.AUDIT_EXCEPTION) { //系统异常
                                    appoint_btn.setClickable(true);
                                }
                            }
                        });
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<RPTokenV3> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
            }
        });


    }
}
