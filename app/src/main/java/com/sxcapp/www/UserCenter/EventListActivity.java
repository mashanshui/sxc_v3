package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.ShareActivity.SelectLottoTypeActivity;
import com.sxcapp.www.UserCenter.Adapter.EventListAdapter;
import com.sxcapp.www.UserCenter.Bean.EventListBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 活动列表界面
 * Created by wenleitao on 2017/12/15.
 */

public class EventListActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.empty_tv)
    TextView empty_tv;
    private UserCenterService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setStatusView(R.color.top_bar_red);
        setTopBarTitle("活动列表", null);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        showProgressDlg();
        getData();
    }

    private void getData() {
        Observable<Result<EventListBean>> ob = service.getActivityList();
        ob.compose(compose(this.<Result<EventListBean>>bindToLifecycle())).subscribe(new BaseObserver<EventListBean>(this) {
            @Override
            protected void onHandleSuccess(final EventListBean eventListBean) {
                removeProgressDlg();
                if (eventListBean.getList().size() > 0) {
                    lv.setAdapter(new EventListAdapter(EventListActivity.this, eventListBean));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if ("1".equals(eventListBean.getList().get(position).getPath())) {
                                startActivity(new Intent(EventListActivity.this, LuckyChooseStoreActivity.class));
                            } else if ("2".equals(eventListBean.getList().get(position).getPath())) {
                                startActivity(new Intent(EventListActivity.this, LotteryActivity.class));
                            } else if ("3".equals(eventListBean.getList().get(position).getPath())) {
                                startActivity(new Intent(EventListActivity.this, SelectLottoTypeActivity.class));
                            }
                        }
                    });
                } else {
                    empty_tv.setVisibility(View.VISIBLE);
                    lv.setEmptyView(empty_tv);
                }
            }
        });
    }


}
