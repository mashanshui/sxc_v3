package com.sxcapp.www.Share.OilShortShare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.OrderListAdapterV3;
import com.sxcapp.www.Share.Bean.OrderListInfoBeanV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecExistInDayOrderActivityV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayEndActivityV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayOrderListActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.BeginUseCarActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.ElecShortOffOrderDetailActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.ElecShortOrderListActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.activity.MainActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import io.reactivex.Observable;

/**
 * 共享汽车订单列表界面
 * Created by wenleitao on 2017/8/4.
 */

public class OilShortOrderListActivityV3 extends BaseActivity implements XListView.IXListViewListener {
    private ShareService service;
    private XListView lv;
    private int pageIndex = 0;
    private int total_page = 1;
    private OrderListAdapterV3 adapter;
    private LinearLayout empty_lin;
    private boolean isFrist = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("燃油车分时租订单", null);
        setStatusView(R.color.top_bar_red);
        setTopBarColor(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        lv = findViewById(R.id.lv);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(false);
        lv.setPullRefreshEnable(true);
        empty_lin = findViewById(R.id.empty_lin);
        showProgressDlg();
        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFrist) {
            loadData();
        }
        isFrist = false;

    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadMore();
    }

    private void loadData() {
        pageIndex = 0;
        Observable<CodeResultV3<OrderListInfoBeanV3>> ob = service.getOilShortOrderListV3(SharedData.getInstance().getString(SharedData._user_id), pageIndex);
        ob.compose(compose(this.<CodeResultV3<OrderListInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderListInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final OrderListInfoBeanV3 infoBeanV3) {
                if (infoBeanV3.getList().size() > 0) {
                    adapter = new OrderListAdapterV3(OilShortOrderListActivityV3.this, infoBeanV3.getList());
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (infoBeanV3.getList().get(position - 1).getOrder_state() == 3) {
                                showToast("无效订单");
                            } else {
                                if (infoBeanV3.getList().get(position - 1).getOrder_state() == 6) {
                                    Intent intent = new Intent(OilShortOrderListActivityV3.this, OilPayActivityV3.class);
                                    intent.putExtra("order_no", infoBeanV3.getList().get(position - 1).getOrder_no());
                                    startActivity(intent);
                                } else if (infoBeanV3.getList().get(position - 1).getOrder_state() == 4 || infoBeanV3.getList().get(position - 1).getOrder_state() == 2) {
                                    Intent intent = new Intent(OilShortOrderListActivityV3.this, OilShortOffOrderDetailActivityV3.class);
                                    intent.putExtra("order_no", infoBeanV3.getList().get(position - 1).getOrder_no());
                                    intent.putExtra("order_state", infoBeanV3.getList().get(position - 1).getOrder_state());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(OilShortOrderListActivityV3.this, OilBeginUseCarActivityV3.class);
                                    intent.putExtra("order_no", infoBeanV3.getList().get(position - 1).getOrder_no());
                                    intent.putExtra("order_state", infoBeanV3.getList().get(position - 1).getOrder_state() + "");
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                    lv.stopRefresh(true);
                    total_page = infoBeanV3.getTotal_page();
                    if (total_page == 1) {
                        lv.setPullLoadEnable(false);
                    } else {
                        lv.setPullLoadEnable(true);
                    }
                } else {
                    lv.setEmptyView(empty_lin);
                }
                removeProgressDlg();
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OrderListInfoBeanV3> value) {
                super.onHandleError(msg, value);
                lv.stopRefresh(false);
                removeProgressDlg();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                lv.stopRefresh(false);
            }
        });
    }

    private void loadMore() {
        pageIndex++;
        Observable<CodeResultV3<OrderListInfoBeanV3>> ob = service.getOilShortOrderListV3(SharedData.getInstance().getString(SharedData._user_id), pageIndex);
        ob.compose(compose(this.<CodeResultV3<OrderListInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<OrderListInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(OrderListInfoBeanV3 orderInfo) {
                lv.stopLoadMore();
                total_page = orderInfo.getTotal_page();
                adapter.loadMore(orderInfo.getList());
                if ((total_page - 1) == pageIndex) {
                    lv.setPullLoadEnable(false);
                } else {
                    lv.setPullLoadEnable(true);
                }

            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<OrderListInfoBeanV3> value) {
                super.onHandleError(msg, value);
                lv.stopLoadMore();
                pageIndex = pageIndex--;
                showToast("加载失败");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                lv.stopLoadMore();
                pageIndex = pageIndex--;
                showToast("加载失败");
            }
        });
    }
}
