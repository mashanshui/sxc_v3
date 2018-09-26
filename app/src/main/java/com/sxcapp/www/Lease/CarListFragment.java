package com.sxcapp.www.Lease;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.sxcapp.www.Base.BaseFragment;
import com.sxcapp.www.Base.SerMap;
import com.sxcapp.www.Bean.CodeObserver;
import com.sxcapp.www.Bean.CodeResult;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.Lease.Bean.LeaseCar;
import com.sxcapp.www.Lease.Bean.LeaseResult;
import com.sxcapp.www.Lease.HttpService.LeaseService;
import com.sxcapp.www.R;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/7/12.
 */

public class CarListFragment extends BaseFragment implements XListView.IXListViewListener {
    private View contentView;
    private XListView xListView;
    private String store_id;
    private String name;
    private int mRefreshState = -1;
    private int STATE_REFRESHING = 0;
    private int STATE_REFRESHING_FINISH = 1;
    private SerMap serMap;
    private Bundle bundle;
    private HashMap<String, String> idMap;
    private ImageView emptyView;
    private int index = 1;
    private int total_page;
    private LeaseAdapter adapter;

    public static CarListFragment newInstance() {
        CarListFragment carListFragment = new CarListFragment();
        return carListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_cartype, container, false);
        }
        store_id = getActivity().getIntent().getStringExtra("store_id");
        xListView = (XListView) contentView.findViewById(R.id.lv);
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
        emptyView = (ImageView) contentView.findViewById(R.id.empty_iv);
        bundle = getArguments();
        name = bundle.getString("name");
        serMap = (SerMap) bundle.getSerializable("map");
        idMap = new HashMap<>();
        idMap = serMap.getMap();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            //更新界面数据，如果数据还在下载中，就显示加载框
            if (mRefreshState == STATE_REFRESHING) {
                ((BaseActivity) (getActivity())).showProgressDlg();
            }
        } else {
            //关闭加载框
            ((BaseActivity) (getActivity())).removeProgressDlg();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mRefreshState = STATE_REFRESHING;
        getType();
    }

    public void getType() {
        LeaseService service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("store_id", store_id);
        map.put("price", "");
        if ("全部".equals(name)) {
            map.put("vehicle_model_custom", "");
        } else {
            String vehicle_brand_id = idMap.get(name);
            map.put("vehicle_model_custom", vehicle_brand_id);
        }
        map.put("desc", "");
        map.put("pageSize", "5");
        map.put("pageIndex", 1 + "");
        map.put("vehicle_brand_id", "");
        Observable<CodeResult<LeaseResult>> carOb = service.getLeaseCarList(map);
        carOb.compose(compose(this.<CodeResult<LeaseResult>>bindToLifecycle())).subscribe(new CodeObserver<LeaseResult>(getActivity()) {
            @Override
            protected void onHandleSuccess(final LeaseResult leaseResult) {
                index = 1;
                xListView.stopRefresh(true);
                total_page = leaseResult.getTotalPage();
                if (total_page > index) {
                    xListView.setPullLoadEnable(true);
                }
                mRefreshState = STATE_REFRESHING_FINISH;
                ((BaseActivity) (getActivity())).removeProgressDlg();
                if (leaseResult.getLeaseList().size() > 0) {
                    adapter = new LeaseAdapter(getActivity(), leaseResult.getLeaseList());
                    final List<LeaseCar> leaseCars = leaseResult.getLeaseList();
                    xListView.setAdapter(adapter);
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            adapter.showAdvanceDialog(leaseCars.get(position - 1), bundle.getString("lease_day"),
                                    bundle.getString("store_id"), bundle.getString("store_name"),
                                    bundle.getString("picker_time"), bundle.getString("g_time"));
                        }
                    });
                } else {
                    xListView.setEmptyView(emptyView);
                }
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                xListView.stopRefresh(false);
                ((BaseActivity) (getActivity())).removeProgressDlg();
                mRefreshState = STATE_REFRESHING_FINISH;
            }
        });
    }

    @Override
    public void onRefresh() {
        getType();
    }

    @Override
    public void onLoadMore() {
        loadMore();
    }

    private void loadMore() {
        LeaseService service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(LeaseService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("store_id", store_id);
        map.put("price", "");
        if ("全部".equals(name)) {
            map.put("vehicle_model_custom", "");
        } else {
            String vehicle_brand_id = idMap.get(name);
            map.put("vehicle_model_custom", vehicle_brand_id);
        }
        map.put("desc", "");
        map.put("pageSize", "5");
        index++;
        map.put("pageIndex", index + "");
        map.put("vehicle_brand_id", "");
        Observable<CodeResult<LeaseResult>> carOb = service.getLeaseCarList(map);
        carOb.compose(compose(this.<CodeResult<LeaseResult>>bindToLifecycle())).subscribe(new CodeObserver<LeaseResult>(getActivity()) {
            @Override
            protected void onHandleSuccess(final LeaseResult leaseResult) {

                total_page = leaseResult.getTotalPage();
                if (total_page > index) {
                    xListView.stopLoadMore();
                } else {
                    xListView.setPullLoadEnable(false);
                    xListView.stopLoadMore("没有数据啦~");
                }

                if (leaseResult.getLeaseList().size() > 0) {
                    adapter.addData(leaseResult.getLeaseList());
                } else {
                    ((BaseActivity) (getActivity())).showToast("加载失败");
                }
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                xListView.stopLoadMore();
            }
        });
    }

    public void loginRefresh() {
        adapter.loginRefresh();
    }
}
