package com.sxcapp.www.UserCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.sxcapp.www.R;
import com.sxcapp.www.UserCenter.Adapter.MessageFrgAdapter;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息中心
 * Created by wenleitao on 2017/8/11.
 */

public class MessageCenterActivity extends BaseActivity implements View.OnClickListener {
    private Button system_btn, activity_btn, my_btn;
    private ViewPager message_vp;
    private MessageFragment sys_frg, my_frg, activity_frg;
    private List<MessageFragment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        setStatusView(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopbarLeftWhiteBackBtn();
        setTopBarTitle("消息中心", null);
        initViews();
    }

    private void initViews() {
        system_btn = (Button) findViewById(R.id.system_btn);
        activity_btn = (Button) findViewById(R.id.activity_btn);
        my_btn = (Button) findViewById(R.id.my_btn);
        message_vp = (ViewPager) findViewById(R.id.message_vp);
        system_btn.setOnClickListener(this);
        activity_btn.setOnClickListener(this);
        my_btn.setOnClickListener(this);
        system_btn.setSelected(true);
        sys_frg = MessageFragment.NewInstance();
        my_frg = MessageFragment.NewInstance();
        activity_frg = MessageFragment.NewInstance();
        Bundle bundle = new Bundle();
        Bundle bundle1 = (Bundle) bundle.clone();
        bundle1.putString("name", "system");
        sys_frg.setArguments(bundle1);
        Bundle bundle2 = (Bundle) bundle.clone();
        bundle2.putString("name", "activity");
        activity_frg.setArguments(bundle2);
        Bundle bundle3 = (Bundle) bundle.clone();
        bundle3.putString("name", "my");
        my_frg.setArguments(bundle3);
        list.add(sys_frg);
        list.add(activity_frg);
        list.add(my_frg);
        MessageFrgAdapter adapter = new MessageFrgAdapter(this.getSupportFragmentManager(), list);
        message_vp.setAdapter(adapter);
        message_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    system_btn.setSelected(true);
                    my_btn.setSelected(false);
                    activity_btn.setSelected(false);
                } else if (position == 1) {
                    system_btn.setSelected(false);
                    my_btn.setSelected(false);
                    activity_btn.setSelected(true);
                } else if (position == 2) {
                    system_btn.setSelected(false);
                    my_btn.setSelected(true);
                    activity_btn.setSelected(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_btn:
                message_vp.setCurrentItem(0);
                break;
            case R.id.activity_btn:
                message_vp.setCurrentItem(1);
                break;
            case R.id.my_btn:
                message_vp.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}
