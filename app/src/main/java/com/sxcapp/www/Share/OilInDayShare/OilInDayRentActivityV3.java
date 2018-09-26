package com.sxcapp.www.Share.OilInDayShare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.security.rp.RPSDK;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.CustomViewPager;
import com.sxcapp.www.CustomerView.DynamicHeightViewPager;
import com.sxcapp.www.Interface.MapLoiIml;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.OilInDayCarAdapterV3;
import com.sxcapp.www.Share.Adapter.RuleAdapter;
import com.sxcapp.www.Share.Bean.OilInDayAppointReturnBeanV3;
import com.sxcapp.www.Share.Bean.OilShareInDayRentCarListBeanV3;
import com.sxcapp.www.Share.Bean.OrderCouponSizeBeanV3;
import com.sxcapp.www.Share.Bean.RPTokenV3;
import com.sxcapp.www.Share.Bean.UnLockCarIsPhoto;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.OrderCouponListActivityV3;
import com.sxcapp.www.Share.RepairActivityV3;
import com.sxcapp.www.Share.StoreDetailActivityV3;
import com.sxcapp.www.Share.UploadCarInfoActivityV3;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.MapUtilContinue;
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
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 油车整租租车界面
 * Created by wenleitao on 2018/4/25.
 */

public class OilInDayRentActivityV3 extends BaseActivity implements View.OnClickListener, MapLoiIml {
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
    @BindView(R.id.rent_day_type_re)
    RelativeLayout rent_day_type_re;
    @BindView(R.id.rent_day_type_tv)
    TextView rent_day_type_tv;
    @BindView(R.id.coupon_size_tv)
    TextView coupon_size_tv;
    @BindView(R.id.coupon_re)
    RelativeLayout coupon_re;
    @BindView(R.id.rent_day_cost_tv)
    TextView rent_day_cost_tv;
    @BindView(R.id.select_g_store_re)
    RelativeLayout select_g_store_re;
    @BindView(R.id.fetch_store_re)
    RelativeLayout fetch_store_re;
    private ShareService service;
    private String fetch_store_id, g_store_id, user_id, fetch_store_name, g_store_name, car_id, rent_day_type_id;
    private OilInDayCarAdapterV3 adapterV3;
    private List<OilShareInDayRentCarListBeanV3.CarListBean> carListBeanList;
    private List<List<String>> ll;
    private List<List<OilShareInDayRentCarListBeanV3.CarListBean.LongSettingListBean>> long_setting_array;
    private RuleAdapter ruleAdapter;
    private List<List<String>> rent_type_list;
    private int index, chose_position;
    List<String> string_temp = new ArrayList<>();
    private OptionsPickerView pvCustomOptions;
    private int no_deductible = 1;
    private boolean is_check = true;
    private double lat, lng;
    private MapUtilContinue mapUtil;
    private int what_use;
    private String coupon_id = "0";
    private String order_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_inday_rent_v3);
        setTopbarLeftWhiteToMainBtn();
        setTopBarTitle("付费租车", null);
        StatusBarUtil.StatusBarDarkMode(this);
        setStatusView(R.color.top_bar_red);
        setTopBarColor(R.color.top_bar_red);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        fetch_store_id = getIntent().getStringExtra("fetch_store_id");
        g_store_id = getIntent().getStringExtra("g_store_id");
        fetch_store_name = getIntent().getStringExtra("fetch_store_name");
        g_store_name = getIntent().getStringExtra("g_store_name");
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        ll = new ArrayList<>();
        rent_type_list = new ArrayList<>();
        long_setting_array = new ArrayList<>();
        mapUtil = new MapUtilContinue(this, this);
        mapUtil.LoPoi();
        loadData();
    }

    private void loadData() {
        fetch_store_name_tv.setText(fetch_store_name);
        g_store_name_tv.setText(g_store_name);
        showProgressDlg();
        Observable<CodeResultV3<OilShareInDayRentCarListBeanV3>> ob = service.getOilInDayCarListV3(fetch_store_id, g_store_id, user_id);
        ob.compose(compose(this.<CodeResultV3<OilShareInDayRentCarListBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OilShareInDayRentCarListBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final OilShareInDayRentCarListBeanV3 beanV3) {
                removeProgressDlg();
                carListBeanList = beanV3.getCar_list();
                adapterV3 = new OilInDayCarAdapterV3(OilInDayRentActivityV3.this, carListBeanList);
                car_vp.setAdapter(adapterV3);
                if (carListBeanList.size() > 0) {
                    mXcircleindicator.setCount(carListBeanList.size()); // specify total count of indicators
                    mXcircleindicator.setSelection(0);
                    mXcircleindicator.setRadius(5);
                }
                car_id = carListBeanList.get(0).getSource_id();
                if (carListBeanList.size() > 0) {
                    for (int i = 0; i < carListBeanList.size(); i++) {
                        ll.add(carListBeanList.get(i).getSettings());
                        long_setting_array.add(carListBeanList.get(i).getLong_setting_list());
                    }
                    for (int i = 0; i < long_setting_array.size(); i++) {
                        string_temp.clear();
                        for (int index = 0; index < long_setting_array.get(i).size(); index++) {
                            OilShareInDayRentCarListBeanV3.CarListBean.LongSettingListBean bean = long_setting_array.get(i).get(index);
                            string_temp.add(bean.getDay_cost() + "天、租金" + bean.getSingle_cost() + "元、" + "押金" + bean.getDeposit_cost() + "元" + "不计免赔" + bean.getNo_deductible_setting() + "元");
                        }
                        rent_type_list.add(string_temp);
                    }


                    ruleAdapter = new RuleAdapter(OilInDayRentActivityV3.this, ll);
                    HashMap<Integer, View> mChildrenViews = new HashMap<Integer, View>();
                    mChildrenViews = ruleAdapter.getmChildrenViews();
                    rules_vp.setmChildrenViews(mChildrenViews);
                    rules_vp.setAdapter(ruleAdapter);
                    rules_vp.resetHeight(0);
                }
                rent_day_type_re.setOnClickListener(OilInDayRentActivityV3.this);
                no_deductible_re.setOnClickListener(OilInDayRentActivityV3.this);
                appoint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(rent_day_type_id)) {
                            showToast("请先选择租车时长");
                        } else {
                            showAlertDlg("确定付费租车", R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    appoint_btn.setClickable(false);
                                    rent_day_type_re.setClickable(false);
                                    no_deductible_re.setClickable(false);
                                    doCheckUser();
                                    removeAlertDlg();
                                }
                            }, R.string.cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    removeAlertDlg();
                                }
                            }, true);

                        }
                    }
                });
                coupon_re.setOnClickListener(OilInDayRentActivityV3.this);
                coupon_re.setClickable(false);
                car_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        car_id = carListBeanList.get(position).getSource_id();
                        mXcircleindicator.setSelection(position);
                        index = position;
                        rules_vp.setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                fetch_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OilInDayRentActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "oil");
                        intent.putExtra("store_id", beanV3.getFetch_store_id());
                        startActivity(intent);
                    }
                });
                select_g_store_re.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OilInDayRentActivityV3.this, StoreDetailActivityV3.class);
                        intent.putExtra("from", "oil");
                        intent.putExtra("store_id", beanV3.getReturn_store_id());
                        startActivity(intent);
                    }
                });
                getPermission();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_day_type_re:
                initCustomOptionPicker();
                break;
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
            case R.id.coupon_re:
                Intent intent = new Intent(OilInDayRentActivityV3.this, OrderCouponListActivityV3.class);
                intent.putExtra("order_no", order_no);
                intent.putExtra("setting_id", rent_day_type_id);
                intent.putExtra("from", "oil_inday");
                startActivityForResult(intent, 123);
                break;
            default:
                break;

        }

    }

    private void doCheckUser() {
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = service.commonDoCheckUser(user_id, 4, rent_day_type_id, no_deductible, car_id, 0 + "");
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object object) {
                appoint_btn.setClickable(true);
                if (AndroidTool.isGetLocationPermission(OilInDayRentActivityV3.this)) {
                    if (lat != 0) {
                        showProgressDlg();
                        appoint_btn.setClickable(false);
                        appointCar();
                    } else {
                        showToast("获取定位失败,请稍后重试");
                    }
                } else {
                    getPermission();
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<Object> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
                no_deductible_re.setClickable(true);
                if ("712".equals(value.getCode()) || "714".equals(value.getCode()) || "718".equals(value.getCode()) ||
                        "720".equals(value.getCode()) || "722".equals(value.getCode())) {
                    try {
                        JSONObject jsonObject = new JSONObject(value.getObj().toString());
                        int add_type = jsonObject.getInt("add_type");
                        String order_no = jsonObject.getString("order_no");
                        String add_cost = jsonObject.getString("add_cost");
                        Intent intent = new Intent(OilInDayRentActivityV3.this, RepairActivityV3.class);
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
                rent_day_type_re.setClickable(true);
                no_deductible_re.setClickable(true);
            }
        });

    }

    private void doRp() {
        Observable<CodeResultV3<RPTokenV3>> ob = service.getRPTokenV3(user_id);
        ob.compose(compose(this.<CodeResultV3<RPTokenV3>>bindToLifecycle())).subscribe(new CodeObserverV3<RPTokenV3>(this) {
            @Override
            protected void onHandleSuccess(RPTokenV3 rpTokenV3) {
                removeProgressDlg();
                RPSDK.start(rpTokenV3.getToken(), OilInDayRentActivityV3.this,
                        new RPSDK.RPCompletedListener() {
                            @Override
                            public void onAuditResult(RPSDK.AUDIT audit) {
                                if (audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
                                    showProgressDlg();
                                    appoint_btn.setClickable(false);
                                    what_use = 1;
                                    getPermission();
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

    private void appointCar() {
        Observable<CodeResultV3<OilInDayAppointReturnBeanV3>> ob = service.oilInDayRentCarV3(user_id, fetch_store_id, g_store_id, car_id, no_deductible, rent_day_type_id, coupon_id, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<OilInDayAppointReturnBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OilInDayAppointReturnBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OilInDayAppointReturnBeanV3 o) {
                removeProgressDlg();
                showToast("租车成功");
                order_no = o.getOrder_no();
                appoint_btn.setClickable(true);
                car_vp.setPagingEnabled(false);
                rent_day_type_re.setClickable(false);
                no_deductible_re.setClickable(false);
                coupon_re.setClickable(false);
                appoint_btn.setText("拍照开锁");
                appoint_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (AndroidTool.isGetLocationPermission(OilInDayRentActivityV3.this)) {
                            if (lat != 0) {
                                showProgressDlg();
                                appoint_btn.setClickable(false);
                                unLockCar();
                            } else {
                                showToast("获取定位失败,请稍后重试");
                            }
                        } else {
                            getPermission();
                        }
                    }
                });
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OilInDayAppointReturnBeanV3> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
                no_deductible_re.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                appoint_btn.setClickable(true);
                rent_day_type_re.setClickable(true);
                no_deductible_re.setClickable(true);
            }
        });
    }

    private void unLockCar() {
        Observable<CodeResultV3<UnLockCarIsPhoto>> ob = service.OilInDayUnLockCarV3(order_no, lat + "", lng + "");
        ob.compose(compose(this.<CodeResultV3<UnLockCarIsPhoto>>bindToLifecycle())).subscribe(new CodeObserverV3<UnLockCarIsPhoto>(this) {
            @Override
            protected void onHandleSuccess(UnLockCarIsPhoto isPhoto) {
                appoint_btn.setClickable(true);
                removeProgressDlg();
                Intent intent = new Intent(OilInDayRentActivityV3.this, OilInDayEndActivityV3.class);
                intent.putExtra("order_no", order_no);
                intent.putExtra("from", "unlock");
                startActivityForResult(intent, 11);
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<UnLockCarIsPhoto> value) {
                super.onHandleError(msg, value);
                appoint_btn.setClickable(true);
                if ("994".equals(value.getCode())) {
                    if (value.getObj().getImage_flag() == 0) {
                        Intent intent = new Intent(OilInDayRentActivityV3.this, UploadCarInfoActivityV3.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("car_id", car_id);
                        intent.putExtra("type", 0);
                        intent.putExtra("order_type", 4);
                        startActivityForResult(intent, 23);

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


    public void getPermission() {
        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
            PermissionGen.with(this)
                    .addRequestCode(100)
                    .permissions(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    .request();
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {

    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {

        showAlertDlg("请开启定位权限", R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeAlertDlg();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }, R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();

            }
        }, true);
    }


    private void getUseCouponSize() {
        showProgressDlg();
        Observable<CodeResultV3<OrderCouponSizeBeanV3>> ob = service.getOilIndayOrderCouponSizeV3(user_id, rent_day_type_id);
        ob.compose(compose(this.<CodeResultV3<OrderCouponSizeBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderCouponSizeBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OrderCouponSizeBeanV3 orderCouponSizeBeanV3) {
                removeProgressDlg();
                if (orderCouponSizeBeanV3.getCoupon_size() == 0) {
                    coupon_size_tv.setText("无可用优惠券");
                    coupon_size_tv.setTextColor(getResources().getColor(R.color.black_tv_26));
                    coupon_re.setClickable(false);
                } else {
                    coupon_size_tv.setText(orderCouponSizeBeanV3.getCoupon_size() + "张可用优惠券");
                    coupon_size_tv.setTextColor(getResources().getColor(R.color.black_tv_87));
                    coupon_re.setClickable(true);
                }
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
                                rent_day_type_tv.setText("租车时长" + long_setting_array.get(index).get(chose_position).getDay_cost() + "天");
                                rent_day_type_id = long_setting_array.get(index).get(chose_position).getSetting_id();
                                rent_day_cost_tv.setVisibility(View.VISIBLE);
                                rent_day_cost_tv.setText("租金" + long_setting_array.get(index).get(chose_position).getSingle_cost() + "元、" + "押金" + long_setting_array.get(index).get(chose_position).getDeposit_cost() + "元");
                                no_deductible_cost_tv.setText(long_setting_array.get(index).get(chose_position).getNo_deductible_setting() + "元");
                                pvCustomOptions.dismiss();
                                getUseCouponSize();
                            }
                        });


                    }
                }).setContentTextSize(15)
                .isDialog(false)
                .setLineSpacingMultiplier(2.5f)
                .build();

//        pvCustomOptions.setPicker(cardItem);//添加数据

        pvCustomOptions.setPicker(rent_type_list.get(index));
        pvCustomOptions.show();
    }

    @Override
    public void onSuccess() {
        lat = mapUtil.getLat();
        lng = mapUtil.getLng();


    }

    @Override
    public void onFail() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                finish();
            } else if (requestCode == 23) {
                showProgressDlg();
                appoint_btn.setClickable(false);
                unLockCar();
            } else if (requestCode == 123) {
                coupon_id = data.getStringExtra("coupon_id");
                coupon_size_tv.setText(data.getStringExtra("coupon_tittle"));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            MyApplication.getInstance().gotoMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapUtil.stopLocation();
    }
}
