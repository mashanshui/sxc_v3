package com.sxcapp.www.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.LuckyAdapter;
import com.sxcapp.www.UserCenter.Bean.LuckyAdapterBean;
import com.sxcapp.www.UserCenter.Bean.LuckyStoreBean;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 抽奖活动选择门店界面
 * Created by wenleitao on 2017/12/14.
 */

public class LuckyChooseStoreActivity extends BaseActivity {
    @BindView(R.id.city_lv)
    ListView city_lv;
    @BindView(R.id.store_lv)
    ListView store_lv;
    private UserCenterService service;
    private LuckyAdapter city_adapter;
    private List<LuckyAdapterBean> city_list;
    private List<List<LuckyAdapterBean>> lists;
    private String city_id;
    private String city_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckychoosestore);
        setTopbarLeftBackBtn();
        setTopBarTitle("选择抽奖门店", null);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        city_list = new ArrayList<>();
        lists = new ArrayList<>();
        getData();
    }

    private void getData() {
        showProgressDlg();
        Observable<Result<LuckyStoreBean>> ob = service.getLuckyStoreList();
        ob.compose(compose(this.<Result<LuckyStoreBean>>bindToLifecycle())).subscribe(new BaseObserver<LuckyStoreBean>(this) {
            @Override
            protected void onHandleSuccess(LuckyStoreBean luckyStoreBean) {
                removeProgressDlg();
                if (luckyStoreBean.getList().size() > 0) {
                    for (int i = 0; i < luckyStoreBean.getList().size(); i++) {
                        List<LuckyAdapterBean> store_list = new ArrayList<>();
                        city_list.add(new LuckyAdapterBean(luckyStoreBean.getList().get(i).getCity_name(), luckyStoreBean.getList().get(i).getCity_id()));
                        for (int z = 0; z < luckyStoreBean.getList().get(i).getStore_list().size(); z++) {
                            store_list.add(new LuckyAdapterBean(luckyStoreBean.getList().get(i).getStore_list().get(z).getStore_name(), luckyStoreBean.getList().get(i).getStore_list().get(z).getStore_id()));
                        }
                        lists.add(store_list);
                    }
                    city_adapter = new LuckyAdapter(LuckyChooseStoreActivity.this, city_list);
                    city_lv.setAdapter(city_adapter);
                    city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            city_id = city_list.get(position).getName();
                            city_adapter.setPosition(position);
                            store_lv.setVisibility(View.VISIBLE);
                            List<LuckyAdapterBean> storeBeanList = new ArrayList<>();
                            storeBeanList = lists.get(position);
                            city_name = city_list.get(position).getName();
                            store_lv.setAdapter(new LuckyAdapter(LuckyChooseStoreActivity.this, storeBeanList));
                            final List<LuckyAdapterBean> finalStoreBeanList = storeBeanList;
                            store_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (city_name.contains("合肥")) {
                                        Intent intent = new Intent(LuckyChooseStoreActivity.this, LuckyActivity.class);
                                        intent.putExtra("city_id", city_id);
                                        intent.putExtra("store_id", finalStoreBeanList.get(position).getId());
                                        startActivity(intent);
                                    } else if (city_name.contains("赤峰")) {
                                        Intent intent = new Intent(LuckyChooseStoreActivity.this, LuckyActivityChifeng.class);
                                        intent.putExtra("city_id", city_id);
                                        intent.putExtra("store_id", finalStoreBeanList.get(position).getId());
                                        startActivity(intent);
                                    } else if (city_name.contains("天津")) {
                                        Intent intent = new Intent(LuckyChooseStoreActivity.this, LuckyActivity.class);
                                        intent.putExtra("city_id", city_id);
                                        intent.putExtra("store_id", finalStoreBeanList.get(position).getId());
                                        startActivity(intent);
                                    }

                                }
                            });
                        }
                    });
                }

            }
        });
    }
}
