package com.sxcapp.www.Buy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Buy.Bean.OldCarListResult;
import com.sxcapp.www.Buy.HttpService.BuyService;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by wenleitao on 2017/8/15.
 * 搜索车辆接口
 */

public class SearchListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private BuyService service;
    private int index = 1;
    private Map<String, String> dataMap;
    private int total_page;
    @BindView(R.id.search_lv)
    XListView lv;
    @BindView(R.id.search_re)
    RelativeLayout search_re;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.empty_iv)
    ImageView empty_iv;
    @BindView(R.id.back_iv)
    ImageView back_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        ButterKnife.bind(this);
        service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(BuyService.class);
        dataMap = new HashMap<>();
        initViews();
    }

    private void initViews() {
        lv.setXListViewListener(this);
        lv.setPullRefreshEnable(false);
        search_re.setOnClickListener(this);
        search_tv.setOnClickListener(this);
        back_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_re:
                InputMethodManager m = (InputMethodManager) search_re.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.search_tv:
                if (TextUtils.isEmpty(search_edit.getText().toString().trim())) {
                    showToast("请输入车名");
                } else {
                    showProgressDlg();
                    dataMap.put("searchText", search_edit.getText().toString().trim());
                    loadData();
                }
                break;
            case R.id.back_iv:
                setBack();
                break;
            default:
                break;

        }

    }

    private void loadData() {
        index = 1;
        dataMap.put("pageIndex", 1 + "");
        Observable<Result<OldCarListResult>> resultOb = service.getOldCarList(dataMap);
        resultOb.compose(compose(this.<Result<OldCarListResult>>bindToLifecycle())).subscribe(new BaseObserver<OldCarListResult>(this) {
            @Override
            protected void onHandleSuccess(final OldCarListResult o) {
                removeProgressDlg();
                total_page = o.getTotalPageNum();
                if (total_page == 1 || total_page == 0) {
                    lv.setPullLoadEnable(false);
                } else {
                    lv.setPullLoadEnable(true);
                }
                if (o.getOldcarlist().size() > 0) {
                    lv.setPullRefreshEnable(true);
                    OldCarAdapter adapter = new OldCarAdapter(SearchListActivity.this, o.getOldcarlist());
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SearchListActivity.this, OldCarDetailActivity.class);
                            intent.putExtra("car_id", o.getOldcarlist().get(position - 1).getId());
                            startActivity(intent);
                        }
                    });
                } else {
                    lv.setPullRefreshEnable(false);
                    OldCarAdapter adapter = new OldCarAdapter(SearchListActivity.this, o.getOldcarlist());
                    lv.setAdapter(adapter);
                    lv.setEmptyView(empty_iv);
                }
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                removeProgressDlg();
                lv.setPullRefreshEnable(false);

            }
        });
    }

    private void refresh() {
        index = 1;
        dataMap.put("pageIndex", 1 + "");
        Observable<Result<OldCarListResult>> resultOb = service.getOldCarList(dataMap);
        resultOb.compose(compose(this.<Result<OldCarListResult>>bindToLifecycle())).subscribe(new BaseObserver<OldCarListResult>(this) {
            @Override
            protected void onHandleSuccess(final OldCarListResult o) {
                lv.stopRefresh(true);
                total_page = o.getTotalPageNum();
                if (total_page == 1 || total_page == 0) {
                    lv.setPullLoadEnable(false);
                } else {
                    lv.setPullLoadEnable(true);
                }
                if (o.getOldcarlist().size() > 0) {
                    OldCarAdapter adapter = new OldCarAdapter(SearchListActivity.this, o.getOldcarlist());
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SearchListActivity.this, OldCarDetailActivity.class);
                            intent.putExtra("car_id", o.getOldcarlist().get(position - 1).getId());
                            startActivity(intent);
                        }
                    });
                } else {
                    lv.setEmptyView(empty_iv);
                }
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopRefresh(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMore() {
        index++;
        dataMap.put("pageIndex", index + "");
        Observable<Result<OldCarListResult>> resultOb = service.getOldCarList(dataMap);
        resultOb.compose(compose(this.<Result<OldCarListResult>>bindToLifecycle())).subscribe(new BaseObserver<OldCarListResult>(this) {
            @Override
            protected void onHandleSuccess(final OldCarListResult o) {
                lv.stopRefresh(true);
                total_page = o.getTotalPageNum();
                if (total_page == index) {
                    lv.setPullLoadEnable(false);
                } else {
                    lv.setPullLoadEnable(true);
                }

                OldCarAdapter adapter = new OldCarAdapter(SearchListActivity.this, o.getOldcarlist());
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchListActivity.this, OldCarDetailActivity.class);
                        intent.putExtra("car_id", o.getOldcarlist().get(position - 1).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected void onHandleError(String msg) {
                super.onHandleError(msg);
                lv.stopLoadMore();
                index--;
                showToast("加载失败");
            }
        });
    }
}
