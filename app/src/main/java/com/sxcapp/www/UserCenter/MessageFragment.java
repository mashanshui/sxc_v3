package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.sxcapp.www.Base.BaseFragment;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.CustomerView.XListView.XListView;
import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.MessageAdapter;
import com.sxcapp.www.UserCenter.Bean.MessageInfo;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.BaseObserver;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManager;

import java.util.HashMap;

import io.reactivex.Observable;


/**
 * 消息中心fragment
 * Created by wenleitao on 2017/8/11.
 */

public class MessageFragment extends BaseFragment implements XListView.IXListViewListener {
    private View contentView;
    private XListView xListView;
    private ImageView emptyView;
    private Bundle bundle;
    private int mRefreshState = -1;
    private int STATE_REFRESHING = 0;
    private int STATE_REFRESHING_FINISH = 1;
    private String user_id = SharedData.getInstance().getString(SharedData._user_id);
    private String name;
    private int index = 1;
    private int total_page;
    private MessageAdapter adapter;

    public static MessageFragment NewInstance() {
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_messagecenter, container, false);
        }
        xListView = (XListView) contentView.findViewById(R.id.lv);
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
        emptyView = (ImageView) contentView.findViewById(R.id.empty_iv);
        bundle = getArguments();
        name = bundle.getString("name");

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
        getMessage();
    }

    private void getMessage() {
        HashMap<String, String> map = new HashMap<>();
        if ("system".equals(name)) {
            map.put("assortment", "0");
        } else if ("activity".equals(name)) {
            map.put("assortment", "1");
        } else {
            map.put("user_id", user_id);
        }
        map.put("pageIndex", "1");
        UserCenterService service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        Observable<Result<MessageInfo>> ob = service.getMessage(map);
        ob.compose(compose(this.<Result<MessageInfo>>bindToLifecycle())).subscribe(new BaseObserver<MessageInfo>(getActivity()) {
            @Override
            protected void onHandleSuccess(final MessageInfo messageInfo) {
                index = 1;
                xListView.stopRefresh(true);
                total_page = messageInfo.getTotalPageNum();
                if (total_page > index) {
                    xListView.setPullLoadEnable(true);
                }
                mRefreshState = STATE_REFRESHING_FINISH;
                ((BaseActivity) (getActivity())).removeProgressDlg();
                if (messageInfo.getList().size() > 0) {
                    adapter = new MessageAdapter(getActivity(), messageInfo.getList());
                    xListView.setAdapter(adapter);
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String title;
                            String target_url;
                            MessageInfo.ListBean bean = messageInfo.getList().get(position - 1);
                            if ("system".equals(name)) {
                                title = "系统消息";
                                if (TextUtils.isEmpty(bean.getUrl())) {
                                    target_url = "http://106.14.135.110:80/SxcH5/activemsg_detail.html?id=" + bean.getId();
                                } else {
                                    target_url = bean.getUrl();
                                }
                                ((BaseActivity) getActivity()).openWebView(target_url, title, true);
                            } else if ("activity".equals(name)) {
                                title = "活动消息";
                                if (bean.getUrl() == null || bean.getUrl().isEmpty()) {
                                    target_url = "http://106.14.135.110:80/SxcH5/activemsg_detail.html?id=" + bean.getId();
                                } else {
                                    target_url = bean.getUrl();
                                }
                                ((BaseActivity) getActivity()).openWebView(target_url, title, true);
                            } else {
                                title = "我的消息";
                                target_url = "http://106.14.135.110:80/SxcH5/activemsg_detail.html?id=" + bean.getId();
                                ((BaseActivity) getActivity()).openWebView(target_url, title, true);

                            }


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
        getMessage();
    }

    @Override
    public void onLoadMore() {
        HashMap<String, String> map = new HashMap<>();
        if ("system".equals(name)) {
            map.put("assortment", "0");
        } else if ("activity".equals(name)) {
            map.put("assortment", "1");
        } else {
            map.put("user_id", user_id);
        }
        index++;
        map.put("pageIndex", index + "");
        UserCenterService service = RetrofitManager.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        Observable<Result<MessageInfo>> ob = service.getMessage(map);
        ob.compose(compose(this.<Result<MessageInfo>>bindToLifecycle())).subscribe(new BaseObserver<MessageInfo>(getActivity()) {
            @Override
            protected void onHandleSuccess(MessageInfo messageInfo) {
                total_page = messageInfo.getTotalPageNum();
                if (total_page > index) {
                    xListView.stopLoadMore();
                } else {
                    xListView.setPullLoadEnable(false);
                    xListView.stopLoadMore("没有数据啦~");
                }
                mRefreshState = STATE_REFRESHING_FINISH;
                ((BaseActivity) (getActivity())).removeProgressDlg();
                if (messageInfo.getList().size() > 0) {
                    adapter.addData(messageInfo.getList());
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


}
