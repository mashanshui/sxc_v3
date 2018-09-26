package com.sxcapp.www.Share.LuxuryShare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Adapter.LuxuryCarListAdapterV3;
import com.sxcapp.www.Share.Adapter.LuxuryCarListRvAdapter;
import com.sxcapp.www.Share.Bean.LuxuryCarListByStoreBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2018/4/27.
 */

public class LuxuryCarListActivityV3 extends BaseActivity implements LuxuryCarListRvAdapter.OnItemClickListener {
    @BindView(R.id.carlist_lv)
    RecyclerView carlist_lv;
    private String fetch_store_id, g_store_id, fetch_store_name, user_id;
    private ShareService service;
    private LuxuryCarListAdapterV3 listAdapterV3;
    private LuxuryCarListByStoreBeanV3 beanV3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_carlistbystore_v3);
        ButterKnife.bind(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarColor(R.color.luxury);
        setStatusView(R.color.luxury);
        StatusBarUtil.StatusBarDarkMode(this);
        fetch_store_name = getIntent().getStringExtra("fetch_store_name");
        fetch_store_id = getIntent().getStringExtra("fetch_store_id");
        g_store_id = getIntent().getStringExtra("g_store_id");
        setTopBarTitle(fetch_store_name + "豪车", null);
        user_id = SharedData.getInstance().getString(SharedData._user_id);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        loadData();
    }

    private void loadData() {
        showProgressDlg();
        Observable<CodeResultV3<LuxuryCarListByStoreBeanV3>> ob = service.getLuxuryCarListByStoreV3(user_id, fetch_store_id, g_store_id);
        ob.compose(compose(this.<CodeResultV3<LuxuryCarListByStoreBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<LuxuryCarListByStoreBeanV3>(this) {
            @Override
            protected void onHandleSuccess(final LuxuryCarListByStoreBeanV3 luxuryCarListByStoreBeanV3) {
                removeProgressDlg();
                beanV3 = luxuryCarListByStoreBeanV3;
                LuxuryCarListRvAdapter adapter = new LuxuryCarListRvAdapter(LuxuryCarListActivityV3.this, luxuryCarListByStoreBeanV3.getCar_list());
                carlist_lv.setLayoutManager(new LinearLayoutManager(LuxuryCarListActivityV3.this));
                carlist_lv.setAdapter(adapter);
                adapter.setmItemClickListener(LuxuryCarListActivityV3.this);
            }
        });
    }

    public void onIvItemClick(int position) {
        Intent intent = new Intent(LuxuryCarListActivityV3.this, LuxuryAppointActivityV3.class);
        intent.putExtra("fetch_store_id", fetch_store_id);
        intent.putExtra("g_store_id", g_store_id);
        intent.putExtra("car_id", beanV3.getCar_list().get(position).getSource_id());
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(LuxuryCarListActivityV3.this, LuxuryAppointActivityV3.class);
        intent.putExtra("fetch_store_id", fetch_store_id);
        intent.putExtra("g_store_id", g_store_id);
        intent.putExtra("car_id", beanV3.getCar_list().get(position).getSource_id());
        startActivity(intent);
    }
}
