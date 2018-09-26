package com.sxcapp.www.UserCenter.EventList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.EventListAdapterV3;
import com.sxcapp.www.UserCenter.Bean.EventListByTypeBeanV3;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/5/17.
 */

public class EventListActivity extends BaseActivity implements EventListAdapterV3.OnItemClickListener {
    @BindView(R.id.carlist_lv)
    RecyclerView rv;
    private int type;
    private UserCenterService service;
    private List<EventListByTypeBeanV3.ActivityBean> list;
    private String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_carlistbystore_v3);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        type = getIntent().getIntExtra("type", 1);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("活动列表", null);
        switch (type) {
            case 1:
                setTopBarColor(R.color.green);
                setStatusView(R.color.green);
                break;
            case 2:
                setTopBarColor(R.color.green);
                setStatusView(R.color.green);
                break;
            case 3:
                setTopBarColor(R.color.top_bar_red);
                setStatusView(R.color.top_bar_red);
                break;
            case 4:
                setTopBarColor(R.color.top_bar_red);
                setStatusView(R.color.top_bar_red);
                break;
            case 5:
                setTopBarColor(R.color.luxury);
                setStatusView(R.color.luxury);
                break;
            default:
                break;
        }
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        list = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<EventListByTypeBeanV3>> ob = service.getEventListByTypeV3(type);
        ob.compose(compose(this.<CodeResultV3<EventListByTypeBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<EventListByTypeBeanV3>(this) {
            @Override
            protected void onHandleSuccess(EventListByTypeBeanV3 eventListByTypeBeanV3) {
                removeProgressDlg();
                list = eventListByTypeBeanV3.getActivity();
                EventListAdapterV3 adapterV3 = new EventListAdapterV3(EventListActivity.this, eventListByTypeBeanV3);
                rv.setLayoutManager(new LinearLayoutManager(EventListActivity.this));
                rv.setAdapter(adapterV3);
                adapterV3.setItemClickListener(EventListActivity.this);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        String targetUrl = list.get(position).getPath() + "?type=" + type + "&customer_id=" + user_id + "&activity_id=" + list.get(position).getId();
        Logger.e(targetUrl);
        openWebView(targetUrl, list.get(position).getTitle(), true);

    }
}
