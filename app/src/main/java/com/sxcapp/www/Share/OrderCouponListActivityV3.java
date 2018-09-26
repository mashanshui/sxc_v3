package com.sxcapp.www.Share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.OrderCouponListAdapterV3;
import com.sxcapp.www.Share.Bean.OrderCouponBeanV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayRentActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.PayActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/20.
 */

public class OrderCouponListActivityV3 extends BaseActivity {
    private ShareService service;
    private String order_no, user_id, setting_id, from;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.empty_iv)
    ImageView empty_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_order_couponlist_v3);
        setTopBarTitle("优惠券列表", null);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        order_no = getIntent()
                .getStringExtra("order_no");
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        from = getIntent().getStringExtra("from");
        if ("elec_short".equals(from) || "elec_inday".equals(from)) {
            setStatusView(R.color.green);
            setTopBarColor(R.color.green);
        } else if ("oil_short".equals(from) || "oil_inday".equals(from)) {
            setStatusView(R.color.top_bar_red);
            setTopBarColor(R.color.top_bar_red);
        }else if("luxury".equals(from)){
            setStatusView(R.color.luxury);
            setTopBarColor(R.color.luxury);
        }
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        if ("elec_short".equals(from)) {
            Observable<CodeResultV3<List<OrderCouponBeanV3>>> ob = service.getPayCouponListV3(order_no);
            ob.compose(compose(this.<CodeResultV3<List<OrderCouponBeanV3>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<OrderCouponBeanV3>>(this) {
                @Override
                protected void onHandleSuccess(final List<OrderCouponBeanV3> couponBeanV3List) {
                    removeProgressDlg();
                    lv.setAdapter(new OrderCouponListAdapterV3(OrderCouponListActivityV3.this, couponBeanV3List));
                    if (couponBeanV3List.size() == 0) {
                        empty_iv.setVisibility(View.VISIBLE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(OrderCouponListActivityV3.this, PayActivityV3.class);
                            intent.putExtra("coupon_id", couponBeanV3List.get(position).getCoupon_id());
                            intent.putExtra("coupon_tittle", couponBeanV3List.get(position).getCoupon_title());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        } else if ("elec_inday".equals(from)) {
            setting_id = getIntent().getStringExtra("setting_id");
            Observable<CodeResultV3<List<OrderCouponBeanV3>>> ob = service.getUsefulCouponListByDayTypeV3(user_id, setting_id);
            ob.compose(compose(this.<CodeResultV3<List<OrderCouponBeanV3>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<OrderCouponBeanV3>>(this) {
                @Override
                protected void onHandleSuccess(final List<OrderCouponBeanV3> couponBeanV3List) {
                    removeProgressDlg();
                    lv.setAdapter(new OrderCouponListAdapterV3(OrderCouponListActivityV3.this, couponBeanV3List));
                    if (couponBeanV3List.size() == 0) {
                        empty_iv.setVisibility(View.VISIBLE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(OrderCouponListActivityV3.this, ElecInDayRentActivityV3.class);
                            intent.putExtra("coupon_id", couponBeanV3List.get(position).getCoupon_id());
                            intent.putExtra("coupon_tittle", couponBeanV3List.get(position).getCoupon_title());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        } else if ("oil_short".equals(from)) {
            Observable<CodeResultV3<List<OrderCouponBeanV3>>> ob = service.getOilShareShortPayCouponListV3(order_no);
            ob.compose(compose(this.<CodeResultV3<List<OrderCouponBeanV3>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<OrderCouponBeanV3>>(this) {
                @Override
                protected void onHandleSuccess(final List<OrderCouponBeanV3> couponBeanV3List) {
                    removeProgressDlg();
                    lv.setAdapter(new OrderCouponListAdapterV3(OrderCouponListActivityV3.this, couponBeanV3List));
                    if (couponBeanV3List.size() == 0) {
                        empty_iv.setVisibility(View.VISIBLE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(OrderCouponListActivityV3.this, PayActivityV3.class);
                            intent.putExtra("coupon_id", couponBeanV3List.get(position).getCoupon_id());
                            intent.putExtra("coupon_tittle", couponBeanV3List.get(position).getCoupon_title());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        } else if ("oil_inday".equals(from)) {
            setting_id = getIntent().getStringExtra("setting_id");
            Observable<CodeResultV3<List<OrderCouponBeanV3>>> ob = service.getOilShareIndayPayCouponListV3(user_id, setting_id);
            ob.compose(compose(this.<CodeResultV3<List<OrderCouponBeanV3>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<OrderCouponBeanV3>>(this) {
                @Override
                protected void onHandleSuccess(final List<OrderCouponBeanV3> couponBeanV3List) {
                    removeProgressDlg();
                    lv.setAdapter(new OrderCouponListAdapterV3(OrderCouponListActivityV3.this, couponBeanV3List));
                    if (couponBeanV3List.size() == 0) {
                        empty_iv.setVisibility(View.VISIBLE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(OrderCouponListActivityV3.this, PayActivityV3.class);
                            intent.putExtra("coupon_id", couponBeanV3List.get(position).getCoupon_id());
                            intent.putExtra("coupon_tittle", couponBeanV3List.get(position).getCoupon_title());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        } else if ("luxury".equals(from)) {
            Observable<CodeResultV3<List<OrderCouponBeanV3>>> ob = service.getLuxuryPayCouponListV3(getIntent().getStringExtra("order_no"));
            ob.compose(compose(this.<CodeResultV3<List<OrderCouponBeanV3>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<OrderCouponBeanV3>>(this) {
                @Override
                protected void onHandleSuccess(final List<OrderCouponBeanV3> couponBeanV3List) {
                    removeProgressDlg();
                    lv.setAdapter(new OrderCouponListAdapterV3(OrderCouponListActivityV3.this, couponBeanV3List));
                    if (couponBeanV3List.size() == 0) {
                        empty_iv.setVisibility(View.VISIBLE);
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(OrderCouponListActivityV3.this, PayActivityV3.class);
                            intent.putExtra("coupon_id", couponBeanV3List.get(position).getCoupon_id());
                            intent.putExtra("coupon_tittle", couponBeanV3List.get(position).getCoupon_title());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        }
    }
}
