package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.Lease.PayDepositActivity;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.OrderListAdapter;
import com.sxcapp.www.UserCenter.Bean.OrderInfo;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import io.reactivex.Observable;

/**
 * 订单列表界面
 * Created by wenleitao on 2017/7/26.
 */

public class OrderListActivity extends BaseActivity implements XListView.IXListViewListener {
    private UserCenterService service;
    private XListView lv;
    private int pageIndex = 1;
    private int pageSize = 5;
    private int total_page = 1;
    private static final int LOADMOREABLE = 11;
    private static final int LOADMOREUNABLE = 12;
    private OrderListAdapter adapter;
    private LinearLayout empty_lin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        setTopbarLeftWhiteBackBtn();
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("租车订单", null);
        lv = (XListView) findViewById(R.id.lv);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(false);
        lv.setPullRefreshEnable(true);
        empty_lin = (LinearLayout) findViewById(R.id.empty_lin);
        showProgressDlg();
        loadData();
    }

    @Override
    public void handleMsg(Message msg) {
        super.handleMsg(msg);
        switch (msg.what) {
            case LOADMOREABLE:
                lv.setPullLoadEnable(true);
                break;
            case LOADMOREUNABLE:
                lv.setPullLoadEnable(false);
                break;
            default:
                break;
        }
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
        pageIndex = 1;
        Observable<Result<OrderInfo>> ob = service.getOrderList(SharedData.getInstance().getString(SharedData._user_id), pageIndex, pageSize);
        ob.compose(compose(this.<Result<OrderInfo>>bindToLifecycle())).subscribe(new BaseObserver<OrderInfo>(this) {
            @Override
            protected void onHandleSuccess(final OrderInfo orderInfo) {
                if (orderInfo.getOrderList().size() > 0) {
                    adapter = new OrderListAdapter(OrderListActivity.this, orderInfo.getOrderList());
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (orderInfo.getOrderList().get(position - 1).getPay_status() == 1 &&
                                    orderInfo.getOrderList().get(position - 1).getDeposit_state() == 0) {
                                Intent intent = new Intent(OrderListActivity.this, PayDepositActivity.class);
                                intent.putExtra("car_id", orderInfo.getOrderList().get(position - 1).getSource_id());
                                intent.putExtra("order_no", orderInfo.getOrderList().get(position - 1).getOrder_no());
                                intent.putExtra("car_name", orderInfo.getOrderList().get(position - 1).getBrand_name());
                                intent.putExtra("img", orderInfo.getOrderList().get(position - 1).getImagePath());
                                intent.putExtra("order_id", orderInfo.getOrderList().get(position - 1).getId());
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(OrderListActivity.this, MyOrderDetailActivity.class);
                                intent.putExtra("car_bean", orderInfo.getOrderList().get(position - 1));
                                intent.putExtra("order_id", orderInfo.getOrderList().get(position - 1).getId());
                                startActivity(intent);
                            }
                        }
                    });
                    lv.stopRefresh(true);
                    total_page = orderInfo.getTotalPageNum();
                    if (total_page > 1) {
                        mHandler.sendEmptyMessage(LOADMOREABLE);
                    } else {
                        mHandler.sendEmptyMessage(LOADMOREUNABLE);
                    }
                } else {
                    lv.setEmptyView(empty_lin);
                }
                removeProgressDlg();
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopRefresh(false);
                removeProgressDlg();
            }
        });
    }

    private void loadMore() {
        pageIndex++;
        Observable<Result<OrderInfo>> ob = service.getOrderList(SharedData.getInstance().getString(SharedData._user_id), pageIndex, pageSize);
        ob.compose(compose(this.<Result<OrderInfo>>bindToLifecycle())).subscribe(new BaseObserver<OrderInfo>(this) {
            @Override
            protected void onHandleSuccess(OrderInfo orderInfo) {
                lv.stopLoadMore();
                total_page = orderInfo.getTotalPageNum();
                adapter.loadMore(orderInfo.getOrderList());
                if (total_page > pageIndex) {
                    mHandler.sendEmptyMessage(LOADMOREABLE);
                } else {
                    mHandler.sendEmptyMessage(LOADMOREUNABLE);
                }

            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopLoadMore();
                pageIndex = pageIndex--;
                showToast("加载失败");
            }
        });
    }

}
