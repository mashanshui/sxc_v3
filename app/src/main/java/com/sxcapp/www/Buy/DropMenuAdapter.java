package com.sxcapp.www.Buy;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.sxcapp.www.Buy.entity.FilterUrl;
import com.sxcapp.www.CustomerView.filter.adapter.MenuAdapter;
import com.sxcapp.www.CustomerView.filter.adapter.SimpleTextAdapter;
import com.sxcapp.www.CustomerView.filter.interfaces.OnFilterDoneListener;
import com.sxcapp.www.CustomerView.filter.interfaces.OnFilterItemClickListener;
import com.sxcapp.www.CustomerView.filter.typeview.SingleListView;
import com.sxcapp.www.CustomerView.filter.util.UIUtil;
import com.sxcapp.www.CustomerView.filter.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createSingleListView();
                break;
            case 1:
                view = createSingleListView01();
                break;
            case 2:
                view = createSingleListView02();
                break;
            case 3:
                view = createSingleListView03();
                break;
            default:
                break;

        }

        return view;
    }

    private View createSingleListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("智能排序");
        list.add("最新发布");
        list.add("价格最低");
        list.add("价格最高");
        list.add("车龄最短");
        list.add("里程最短");
        singleListView.setList(list, -1);

        return singleListView;
    }

    private View createSingleListView01() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 1;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("大众");
        list.add("江淮");
        list.add("福特");
        list.add("雪佛兰");
        list.add("别克");
        list.add("起亚");
        singleListView.setList(list, -1);

        return singleListView;
    }

    private View createSingleListView02() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("3万以下");
        list.add("3-5万");
        list.add("5-7万");
        list.add("7-9万");
        list.add("9-12万");
        list.add("12-15万");
        list.add("15-17万");
        list.add("17万以上");
        singleListView.setList(list, -1);

        return singleListView;
    }

    private View createSingleListView03() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;
                        FilterUrl.instance().position = 3;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("不限");
        list.add("跑车");
        list.add("MPV");
        list.add("轿车");
        list.add("SUV");
        singleListView.setList(list, -1);
        return singleListView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(0, "", "");
        }
    }

}
