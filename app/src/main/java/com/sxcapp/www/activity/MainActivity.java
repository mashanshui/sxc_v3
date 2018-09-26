package com.sxcapp.www.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.rd.PageIndicatorView;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Buy.BuyActivity;
import com.sxcapp.www.CustomerView.CircleTransformt;
import com.sxcapp.www.Lease.LeaseActivity;
import com.sxcapp.www.Login.LoginActivity;
import com.sxcapp.www.MyApplication;
import com.sxcapp.www.R;
import com.sxcapp.www.Sell.SellActivity;
import com.sxcapp.www.Share.Bean.ExistOrderBeanV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecExistInDayOrderActivityV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElecInDayEndActivityV3;
import com.sxcapp.www.Share.ElectricInDayShare.ElectricInDayShareActivityV3;
import com.sxcapp.www.Share.ElectricShareActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.BeginUseCarActivityV3;
import com.sxcapp.www.Share.ElectricShortShare.PayActivityV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.Share.LuxuryShare.LuxuryConfirmDayTypeActivityV3;
import com.sxcapp.www.Share.LuxuryShare.LuxuryEndActivityV3;
import com.sxcapp.www.Share.LuxuryShare.LuxuryPreAuditActivityV3;
import com.sxcapp.www.Share.LuxuryShare.LuxuryRentCarActivityV3;
import com.sxcapp.www.Share.LuxuryShare.LuxuryShareActivityV3;
import com.sxcapp.www.Share.OilInDayShare.OilExistInDayOrderActivityV3;
import com.sxcapp.www.Share.OilInDayShare.OilInDayEndActivityV3;
import com.sxcapp.www.Share.OilInDayShare.OilInDayShareActivityV3;
import com.sxcapp.www.Share.OilShortShare.OilBeginUseCarActivityV3;
import com.sxcapp.www.Share.OilShortShare.OilPayActivityV3;
import com.sxcapp.www.Share.OilShortShare.OilShortShareActivityV3;
import com.sxcapp.www.UserCenter.Adapter.AdvertisePageAdapter;
import com.sxcapp.www.UserCenter.AutonymActivity;
import com.sxcapp.www.UserCenter.Bean.UserInfoBeanV3;
import com.sxcapp.www.UserCenter.CreditAuthenticationActivityV3;
import com.sxcapp.www.UserCenter.EventList.EventGuideActivityV3;
import com.sxcapp.www.UserCenter.HelpCenterActivity;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.UserCenter.InviteActivity;
import com.sxcapp.www.UserCenter.MessageCenterActivity;
import com.sxcapp.www.UserCenter.MyOrderGuideActivityV3;
import com.sxcapp.www.UserCenter.MyWalletActivity_v3;
import com.sxcapp.www.UserCenter.RecommendStoreActivityV3;
import com.sxcapp.www.UserCenter.SetActivity;
import com.sxcapp.www.UserCenter.UserProfilActivity;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.Util.TimeFormat;
import com.sxcapp.www.Util.UserInfoMessageEvent;
import com.sxcapp.www.webtool.RetrofitManagerV3;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * @author wenleitao
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.button_content_lin)
    LinearLayout button_content_lin;
    @BindView(R.id.top_bar_re)
    RelativeLayout top_bar_re;
    @BindView(R.id.avatar_iv)
    ImageView avatar_iv;
    @BindView(R.id.user_name_tv)
    TextView user_name_tv;
    @BindView(R.id.authentication_iv)
    ImageView authentication_iv;
    @BindView(R.id.authentication_tv)
    TextView authentication_tv;
    @BindView(R.id.member_level_tv)
    TextView member_level_tv;
    @BindView(R.id.rent_count_tv)
    TextView rent_count_tv;
    @BindView(R.id.balance_tv)
    TextView balance_tv;
    @BindView(R.id.integral_tv)
    TextView integral_tv;
    @BindView(R.id.my_order_re)
    RelativeLayout my_order_re;
    @BindView(R.id.my_wallet_re)
    RelativeLayout my_wallet_re;
    @BindView(R.id.member_level_re)
    RelativeLayout member_level_re;
    @BindView(R.id.activity_re)
    RelativeLayout activity_re;
    @BindView(R.id.help_center_re)
    RelativeLayout help_center_re;
    @BindView(R.id.authentication_re)
    RelativeLayout authentication_re;
    @BindView(R.id.credit_authentication_re)
    RelativeLayout credit_authentication_re;
    @BindView(R.id.set_re)
    RelativeLayout set_re;
    @BindView(R.id.remind_re)
    RelativeLayout remind_re;
    @BindView(R.id.user_center_lin)
    LinearLayout user_center_lin;
    @BindView(R.id.help_tips_tv)
    TextView help_tips_tv;
    @BindView(R.id.activity_tips_tv)
    TextView activity_tips_tv;
    @BindView(R.id.remind_car_iv)
    ImageView remind_car_iv;
    @BindView(R.id.avatar_re)
    RelativeLayout avatar_re;
    @BindView(R.id.lv_re)
    RelativeLayout lv_re;
    @BindView(R.id.credit_authentication_iv)
    ImageView credit_authentication_iv;
    @BindView(R.id.credit_authentication_tv)
    TextView credit_authentication_tv;
    @BindView(R.id.message_center_lin)
    LinearLayout message_center_lin;
    @BindView(R.id.invite_lin)
    LinearLayout invite_lin;
    @BindView(R.id.remind_tv)
    TextView remind_tv;
    @BindView(R.id.recommend_store_re)
    RelativeLayout recommend_store_re;


    private int screen_width, screen_height;
    private int top_bar_height;
    private int img_length, top_interval, img_interval, icon_top_interval, tv_bottom_interval, bottom_interval, re_side_interval;
    private long exitTime;
    private static final int USERINFO_MSG = 23;
    private UserCenterService service;
    private UserInfoMessageEvent event;
    private boolean isShow = true;
    private String ad_imageUrl;
    private Dialog adv_dialog;
    private long current_time;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private List<String> ad_imageStr_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v3);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        initViews();
        String from = getIntent().getStringExtra("from");
        if ("user".equals(from)) {
            openDrawerLayout();
        }
        StatusBarUtil.StatusBarDarkMode(this);
        EventBus.getDefault().register(this);
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(1000);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(650);
        ad_imageStr_list = new ArrayList<>();
        checkIsHaveActivity();


    }


    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     *
     * @param
     */
    private void fitsSystemWindows() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(android.R.id.content);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            //布局预留状态栏高度的 padding
            parentView.setFitsSystemWindows(true);
            if (parentView instanceof DrawerLayout) {
                DrawerLayout drawer = (DrawerLayout) parentView;
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                drawer.setClipToPadding(false);
            }
        }
    }


    /**
     * 添加状态栏占位视图
     *
     * @param
     */
    private void addStatusViewWithColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //要在内容布局增加状态栏，否则会盖在侧滑菜单上
            ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
            //DrawerLayout 则需要在第一个子视图即内容试图中添加padding
            View parentView = rootView.getChildAt(0);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.top_bar_red));
            //添加占位状态栏到线性布局中
            linearLayout.addView(statusBarView, lp);
            //侧滑菜单
            DrawerLayout drawer = (DrawerLayout) parentView;
            //内容视图
            View content = findViewById(R.id.content_re);
            //将内容视图从 DrawerLayout 中移除
            drawer.removeView(content);
            //添加内容视图
            linearLayout.addView(content, content.getLayoutParams());
            //将带有占位状态栏的新的内容视图设置给 DrawerLayout
            drawer.addView(linearLayout, 0);
        }

    }


    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void initViews() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels;
        //测量方法
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        top_bar_re.measure(width, height);
        top_bar_height = top_bar_re.getMeasuredHeight();
        img_length = AndroidTool.dip2px(this, 33.33f);
        top_interval = AndroidTool.dip2px(this, 18.7f);
        bottom_interval = AndroidTool.dip2px(this, 54);
        img_interval = AndroidTool.dip2px(this, 5.33f);
        icon_top_interval = AndroidTool.dip2px(this, 26.67f);
        tv_bottom_interval = AndroidTool.dip2px(this, 24);
        re_side_interval = AndroidTool.dip2px(this, 19);

        fitsSystemWindows();
        addStatusViewWithColor();

        ViewGroup.LayoutParams layoutParams_img = new ViewGroup.LayoutParams(img_length, img_length);
        ViewGroup.LayoutParams layoutParams_tv = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams lp_tv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, icon_top_interval, 0, 0);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);//这个就是添加其他属性的，这个是在父元素的底部。
        lp_tv.setMargins(0, 0, 0, tv_bottom_interval);
        lp_tv.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp_tv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        int re_width = (screen_width - 2 * re_side_interval - img_interval) / 2;
        int re_height = (screen_height - top_interval - bottom_interval - top_bar_height - getStatusBarHeight() - img_interval * 3) / 4;

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);
        layoutParams.setMargins(0, img_interval, 0, 0);

        LinearLayout linearLayout02 = new LinearLayout(this);
        linearLayout02.setLayoutParams(layoutParams);
        LinearLayout linearLayout03 = new LinearLayout(this);
        linearLayout03.setLayoutParams(layoutParams);
        LinearLayout linearLayout04 = new LinearLayout(this);
        linearLayout04.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams re_layoutParams = new RelativeLayout.LayoutParams(re_width, re_height);
        RelativeLayout.LayoutParams re_layoutParams_margin = new RelativeLayout.LayoutParams(re_width, re_height);
        re_layoutParams_margin.setMargins(img_interval, 0, 0, 0);
        LinearLayout.LayoutParams lin_layoutParams_margin = new LinearLayout.LayoutParams(re_width, re_height);
        lin_layoutParams_margin.setMargins(img_interval, 0, 0, 0);

        RelativeLayout elec_short_share_re = new RelativeLayout(this);
        elec_short_share_re.setLayoutParams(re_layoutParams);
//        elec_short_share_re.setBackgroundColor(getResources().getColor(R.color.white));
        elec_short_share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView elec_short_icon = new ImageView(this);
        elec_short_icon.setBackgroundResource(R.mipmap.elec_short_back_v3);
        elec_short_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img = new RelativeLayout(this);
        relativeLayout_img.setLayoutParams(lp);
        relativeLayout_img.addView(elec_short_icon);
        elec_short_share_re.addView(relativeLayout_img);
        RelativeLayout relativeLayout_tv = new RelativeLayout(this);
        relativeLayout_tv.setLayoutParams(lp_tv);
        TextView tv = new TextView(this);
        tv.setLayoutParams(layoutParams_tv);
        tv.setText("电动分时");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black_tv));
        tv.setTextSize(13);
        relativeLayout_tv.addView(tv);
        elec_short_share_re.addView(relativeLayout_tv);
        linearLayout.addView(elec_short_share_re);

        RelativeLayout elec_inday_share_re = new RelativeLayout(this);
        elec_inday_share_re.setLayoutParams(lin_layoutParams_margin);
//        elec_inday_share_re.setBackgroundColor(getResources().getColor(R.color.white));
        elec_inday_share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView elec_inday_icon = new ImageView(this);
        elec_inday_icon.setBackgroundResource(R.mipmap.elec_inday_back_v3);
        elec_inday_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img02 = new RelativeLayout(this);
        relativeLayout_img02.setLayoutParams(lp);
        relativeLayout_img02.addView(elec_inday_icon);
        elec_inday_share_re.addView(relativeLayout_img02);
        RelativeLayout relativeLayout_tv02 = new RelativeLayout(this);
        relativeLayout_tv02.setLayoutParams(lp_tv);
        TextView tv02 = new TextView(this);
        tv02.setLayoutParams(layoutParams_tv);
        tv02.setText("电动长租");
        tv02.setGravity(Gravity.CENTER);
        tv02.setTextColor(getResources().getColor(R.color.black_tv));
        tv02.setTextSize(13);
        relativeLayout_tv02.addView(tv02);
        elec_inday_share_re.addView(relativeLayout_tv02);
        linearLayout.addView(elec_inday_share_re);

        RelativeLayout oil_short_share_re = new RelativeLayout(this);
        oil_short_share_re.setLayoutParams(re_layoutParams);
//        oil_short_share_re.setBackgroundColor(getResources().getColor(R.color.white));
        oil_short_share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView oil_share_icon = new ImageView(this);
        oil_share_icon.setBackgroundResource(R.mipmap.oil_short_share_back_v3);
        oil_share_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img03 = new RelativeLayout(this);
        relativeLayout_img03.setLayoutParams(lp);
        relativeLayout_img03.addView(oil_share_icon);
        oil_short_share_re.addView(relativeLayout_img03);
        RelativeLayout relativeLayout_tv03 = new RelativeLayout(this);
        relativeLayout_tv03.setLayoutParams(lp_tv);
        TextView tv03 = new TextView(this);
        tv03.setLayoutParams(layoutParams_tv);
        tv03.setText("燃油分时");
        tv03.setGravity(Gravity.CENTER);
        tv03.setTextColor(getResources().getColor(R.color.black_tv));
        tv03.setTextSize(13);
        relativeLayout_tv03.addView(tv03);
        oil_short_share_re.addView(relativeLayout_tv03);
        linearLayout02.addView(oil_short_share_re);

        RelativeLayout oil_inday_share_re = new RelativeLayout(this);
        oil_inday_share_re.setLayoutParams(lin_layoutParams_margin);
//        oil_inday_share_re.setBackgroundColor(getResources().getColor(R.color.white));
        oil_inday_share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView oil_inday_icon = new ImageView(this);
        oil_inday_icon.setBackgroundResource(R.mipmap.oil_inday_share_back_v3);
        oil_inday_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img04 = new RelativeLayout(this);
        relativeLayout_img04.setLayoutParams(lp);
        relativeLayout_img04.addView(oil_inday_icon);
        oil_inday_share_re.addView(relativeLayout_img04);
        RelativeLayout relativeLayout_tv04 = new RelativeLayout(this);
        relativeLayout_tv04.setLayoutParams(lp_tv);
        TextView tv04 = new TextView(this);
        tv04.setLayoutParams(layoutParams_tv);
        tv04.setText("燃油长租");
        tv04.setGravity(Gravity.CENTER);
        tv04.setTextColor(getResources().getColor(R.color.black_tv));
        tv04.setTextSize(13);
        relativeLayout_tv04.addView(tv04);
        oil_inday_share_re.addView(relativeLayout_tv04);
        linearLayout02.addView(oil_inday_share_re);

        RelativeLayout luxury_inday_share_re = new RelativeLayout(this);
        luxury_inday_share_re.setLayoutParams(re_layoutParams);
//        luxury_inday_share_re.setBackgroundColor(getResources().getColor(R.color.white));
        luxury_inday_share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView luxury_inday_icon = new ImageView(this);
        luxury_inday_icon.setBackgroundResource(R.mipmap.luxury_share_back_v3);
        luxury_inday_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img05 = new RelativeLayout(this);
        relativeLayout_img05.setLayoutParams(lp);
        relativeLayout_img05.addView(luxury_inday_icon);
        luxury_inday_share_re.addView(relativeLayout_img05);
        RelativeLayout relativeLayout_tv05 = new RelativeLayout(this);
        relativeLayout_tv05.setLayoutParams(lp_tv);
        TextView tv05 = new TextView(this);
        tv05.setLayoutParams(layoutParams_tv);
        tv05.setText("豪车长租");
        tv05.setGravity(Gravity.CENTER);
        tv05.setTextColor(getResources().getColor(R.color.black_tv));
        tv05.setTextSize(13);
        relativeLayout_tv05.addView(tv05);
        luxury_inday_share_re.addView(relativeLayout_tv05);
        linearLayout03.addView(luxury_inday_share_re);

        RelativeLayout share_re = new RelativeLayout(this);
        share_re.setLayoutParams(lin_layoutParams_margin);
//        share_re.setBackgroundColor(getResources().getColor(R.color.white));
        share_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView share_icon = new ImageView(this);
        share_icon.setBackgroundResource(R.mipmap.share_back_v3);
        share_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img06 = new RelativeLayout(this);
        relativeLayout_img06.setLayoutParams(lp);
        relativeLayout_img06.addView(share_icon);
        share_re.addView(relativeLayout_img06);
        RelativeLayout relativeLayout_tv06 = new RelativeLayout(this);
        relativeLayout_tv06.setLayoutParams(lp_tv);
        TextView tv06 = new TextView(this);
        tv06.setLayoutParams(layoutParams_tv);
        tv06.setText("随心租赁");
        tv06.setGravity(Gravity.CENTER);
        tv06.setTextColor(getResources().getColor(R.color.black_tv));
        tv06.setTextSize(13);
        relativeLayout_tv06.addView(tv06);
        share_re.addView(relativeLayout_tv06);
        linearLayout03.addView(share_re);

        RelativeLayout buy_re = new RelativeLayout(this);
        buy_re.setLayoutParams(re_layoutParams);
//        buy_re.setBackgroundColor(getResources().getColor(R.color.white));
        buy_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView buy_icon = new ImageView(this);
        buy_icon.setBackgroundResource(R.mipmap.buy_back_v3);
        buy_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img07 = new RelativeLayout(this);
        relativeLayout_img07.setLayoutParams(lp);
        relativeLayout_img07.addView(buy_icon);
        buy_re.addView(relativeLayout_img07);
        RelativeLayout relativeLayout_tv07 = new RelativeLayout(this);
        relativeLayout_tv07.setLayoutParams(lp_tv);
        TextView tv07 = new TextView(this);
        tv07.setLayoutParams(layoutParams_tv);
        tv07.setText("买车");
        tv07.setGravity(Gravity.CENTER);
        tv07.setTextColor(getResources().getColor(R.color.black_tv));
        tv07.setTextSize(13);
        relativeLayout_tv07.addView(tv07);
        buy_re.addView(relativeLayout_tv07);
        linearLayout04.addView(buy_re);

        RelativeLayout sell_re = new RelativeLayout(this);
        sell_re.setLayoutParams(lin_layoutParams_margin);
//        sell_re.setBackgroundColor(getResources().getColor(R.color.white));
        sell_re.setBackgroundResource(R.drawable.selector_view_click_white_gray);
        ImageView sell_icon = new ImageView(this);
        sell_icon.setBackgroundResource(R.mipmap.sell_back_v3);
        sell_icon.setLayoutParams(layoutParams_img);
        RelativeLayout relativeLayout_img08 = new RelativeLayout(this);
        relativeLayout_img08.setLayoutParams(lp);
        relativeLayout_img08.addView(sell_icon);
        sell_re.addView(relativeLayout_img08);
        RelativeLayout relativeLayout_tv08 = new RelativeLayout(this);
        relativeLayout_tv08.setLayoutParams(lp_tv);
        TextView tv08 = new TextView(this);
        tv08.setLayoutParams(layoutParams_tv);
        tv08.setText("卖车");
        tv08.setGravity(Gravity.CENTER);
        tv08.setTextColor(getResources().getColor(R.color.black_tv));
        tv08.setTextSize(13);
        relativeLayout_tv08.addView(tv08);
        sell_re.addView(relativeLayout_tv08);
        linearLayout04.addView(sell_re);

        button_content_lin.addView(linearLayout);
        button_content_lin.addView(linearLayout02);
        button_content_lin.addView(linearLayout03);
        button_content_lin.addView(linearLayout04);

        share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaseActivity.class);
                startActivity(intent);
            }
        });
        buy_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_buy = new Intent(MainActivity.this, BuyActivity.class);
                startActivity(intent_buy);
            }
        });

        sell_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sell = new Intent(MainActivity.this, SellActivity.class);
                startActivity(intent_sell);
            }
        });
        elec_short_share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_share = new Intent(MainActivity.this, ElectricShareActivityV3.class);
                startActivity(intent_share);
            }
        });
        elec_inday_share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_share = new Intent(MainActivity.this, ElectricInDayShareActivityV3.class);
                startActivity(intent_share);
            }
        });
        oil_short_share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_share = new Intent(MainActivity.this, OilShortShareActivityV3.class);
                startActivity(intent_share);
            }
        });
        oil_inday_share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_share = new Intent(MainActivity.this, OilInDayShareActivityV3.class);
                startActivity(intent_share);
            }
        });
        luxury_inday_share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_share = new Intent(MainActivity.this, LuxuryShareActivityV3.class);
                startActivity(intent_share);
            }
        });
        my_wallet_re.setOnClickListener(this);
        user_center_lin.setOnClickListener(this);
        set_re.setOnClickListener(this);
        my_order_re.setOnClickListener(this);
        credit_authentication_re.setOnClickListener(this);
        authentication_re.setOnClickListener(this);
        avatar_re.setOnClickListener(this);
        member_level_re.setOnClickListener(this);
        lv_re.setOnClickListener(this);
        help_center_re.setOnClickListener(this);
        activity_re.setOnClickListener(this);
        message_center_lin.setOnClickListener(this);
        invite_lin.setOnClickListener(this);
        recommend_store_re.setOnClickListener(this);
        if (SharedData.getInstance().isLogin()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            //关闭手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                loadUserInfoData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.e("onResume");
        if (SharedData.getInstance().isLogin()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            //关闭手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        checkIsHaveShareOrder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.user_center_lin:
                if (SharedData.getInstance().isLogin()) {
                    openDrawerLayout();
                } else {
                    Intent intent_register = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent_register, LOGIN_REQUEST);
                }
                break;
            case R.id.my_order_re:
                Intent intent_order = new Intent(MainActivity.this, MyOrderGuideActivityV3.class);
                startActivity(intent_order);
                break;
            case R.id.autonym_lin:
                Intent intent_auto = new Intent(MainActivity.this, AutonymActivity.class);
                startActivity(intent_auto);
                break;
            case R.id.my_wallet_re:
                Intent intent_wallet = new Intent(MainActivity.this, MyWalletActivity_v3.class);
                startActivity(intent_wallet);
                break;
            case R.id.set_re:
                Intent intent_set = new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent_set);
                break;
            case R.id.avatar_re:
                Intent intent_profile = new Intent(MainActivity.this, UserProfilActivity.class);
                startActivity(intent_profile);
                break;
            case R.id.help_center_re:
                Intent intent_help = new Intent(MainActivity.this, HelpCenterActivity.class);
                startActivity(intent_help);
                break;
            case R.id.invite_lin:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent_gift = new Intent(MainActivity.this, InviteActivity.class);
                    startActivity(intent_gift);
                } else {
                    Intent intent_gift = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent_gift, LOGIN_REQUEST);
                }
                break;
            case R.id.message_center_lin:
                if (SharedData.getInstance().isLogin()) {
                    Intent intent_message = new Intent(MainActivity.this, MessageCenterActivity.class);
                    startActivity(intent_message);
                } else {
                    Intent intent_message = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent_message, LOGIN_REQUEST);
                }
                break;
            case R.id.member_level_re:
                openWebView("http://106.14.135.110/SxcH5/mylevel.html?userId=" +
                        SharedData.getInstance().getString(SharedData._user_id), "会员等级", true);
                break;
            case R.id.activity_re:
                startActivity(new Intent(MainActivity.this, EventGuideActivityV3.class));
                break;
            case R.id.credit_authentication_re:
                startActivity(new Intent(MainActivity.this, CreditAuthenticationActivityV3.class));
                break;
            case R.id.authentication_re:
                startActivity(new Intent(MainActivity.this, AutonymActivity.class));
                break;
            case R.id.lv_re:
                openWebView("http://106.14.135.110/SxcH5/howtoupgrade.html", "如何积分", true);
                break;
            case R.id.recommend_store_re:
                startActivity(new Intent(MainActivity.this, RecommendStoreActivityV3.class));

                break;
            default:
                break;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // do something what you want
            ExitApp();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().systemExit();
        }
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case USERINFO_MSG:
//                if (TextUtils.isEmpty(event.getAvatar())) {
//                    if (!TextUtils.isEmpty(event.getNick_name())) {
//                        user_name_tv.setText(event.getNick_name());
//                    }
//
//                } else {
//                    RequestOptions options = new RequestOptions();
//                    options.transform(new CircleTransformt(MainActivity.this));
//                    Glide.with(MainActivity.this).load(Properties.baseImageUrl +
//                            event.getAvatar()).apply(options).into(avatar_iv);
//                    user_name_tv.setText(event.getNick_name());
//                }
                break;
            default:
                break;
        }
    }

    /**
     * 打开侧边栏
     */
    public void openDrawerLayout() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOGIN_REQUEST) {
                if (SharedData.getInstance().isLogin()) {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    //关闭手势滑动
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(UserInfoMessageEvent messageEvent) {
        event = messageEvent;
//         更新UI不放在主线程中 防止ANR
        if (TextUtils.isEmpty(event.getAvatar())) {
            if (!TextUtils.isEmpty(event.getNick_name())) {
                user_name_tv.setText(event.getNick_name());
            }

        } else {
            RequestOptions options = new RequestOptions();
            options.transform(new CircleTransformt(MainActivity.this));
            Glide.with(MainActivity.this).load(
                    event.getAvatar()).apply(options).into(avatar_iv);
            if (!TextUtils.isEmpty(event.getNick_name())) {
                user_name_tv.setText(event.getNick_name());
            }
        }
    }

    /**
     * 检查活动dialog是否弹出
     * 每天只弹出一次
     */
    public void checkIsHaveActivity() {
        UserCenterService service02 = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);

        Observable<CodeResultV3<List<String>>> obV3 = service02.isHaveActivityV3();
        obV3.compose(compose(this.<CodeResultV3<List<String>>>bindToLifecycle())).subscribe(new CodeObserverV3<List<String>>(this) {
            @Override
            protected void onHandleSuccess(List<String> ad_list) {
                if (ad_list.size() > 0) {
                    String str = SharedData.getInstance().getString(SharedData._everyday_login);
                    current_time = System.currentTimeMillis();
                    ad_imageStr_list = ad_list;
                    if (TextUtils.isEmpty(str)) {
                        showAdvertisingWindow();
                        SharedData.getInstance().set(SharedData._everyday_login, current_time + "");
                    } else {
//                        不为空，对比时间，是第二天则弹出
                        if (!TimeFormat.isToday(str)) {
                            showAdvertisingWindow();
                            SharedData.getInstance().set(SharedData._everyday_login, current_time + "");
                        }
                    }
                }
            }
        });

    }

    private void loadUserInfoData() {
        Observable<CodeResultV3<UserInfoBeanV3>> ob = service.getUserInfoV3(SharedData.getInstance().getString(SharedData._user_id));
        ob.compose(compose(this.<CodeResultV3<UserInfoBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<UserInfoBeanV3>(this) {
            @Override
            protected void onHandleSuccess(UserInfoBeanV3 userInfoBeanV3) {
                if (userInfoBeanV3.getHead_image() != null) {
                    RequestOptions options = new RequestOptions();
                    options.transform(new CircleTransformt(MainActivity.this));
                    options.error(R.mipmap.default_avatar_v3);
                    Glide.with(MainActivity.this).load(
                            userInfoBeanV3.getHead_image()).apply(options).into(avatar_iv);
                    SharedData.getInstance().set(SharedData._avatar, userInfoBeanV3.getHead_image());
                }
                if (userInfoBeanV3.getNick_name() != null && userInfoBeanV3.getNick_name().length() > 0) {
                    user_name_tv.setText(userInfoBeanV3.getNick_name());
                    SharedData.getInstance().set(SharedData._user_name, userInfoBeanV3.getNick_name());
                }
                if (userInfoBeanV3.getIs_authen() == 0) {
                    authentication_iv.setBackgroundResource(R.mipmap.un_authentication_icon_v3);
                    authentication_tv.setText("未认证");

                } else if (userInfoBeanV3.getIs_authen() == 1) {
                    authentication_iv.setBackgroundResource(R.mipmap.navi_auth_icon_v3);
                    authentication_tv.setText("已认证");
                }
                if (userInfoBeanV3.getIs_credit() == 0) {
                    credit_authentication_iv.setBackgroundResource(R.mipmap.credit_auth_fail_v3);
                    credit_authentication_tv.setText("未认证");
                } else if (userInfoBeanV3.getIs_credit() == 1) {
                    credit_authentication_iv.setBackgroundResource(R.mipmap.navi_credit_icon_v3);
                    credit_authentication_tv.setText("已认证");
                }
                rent_count_tv.setText(userInfoBeanV3.getRentals() + "次");
                balance_tv.setText(userInfoBeanV3.getBalance() + "元");
                integral_tv.setText(userInfoBeanV3.getIntegral() + "分");
                member_level_tv.setText(userInfoBeanV3.getMember_level());
                activity_tips_tv.setText(userInfoBeanV3.getActivity_tips());
                help_tips_tv.setText(userInfoBeanV3.getHelp_tips());

            }
        });
    }

    /**
     * 判断是否有未完成订单
     */
    public void checkIsHaveShareOrder() {
        if (SharedData.getInstance().isLogin()) {
            showProgressDlg();
            ShareService service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
            Observable<CodeResultV3<ExistOrderBeanV3>> ob = service.checkIsHaveShareOrderV3(SharedData.getInstance().getString(SharedData._user_id));
            ob.compose(compose(this.<CodeResultV3<ExistOrderBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ExistOrderBeanV3>(this) {
                @Override
                protected void onHandleSuccess(final ExistOrderBeanV3 o) {
                    removeProgressDlg();
                    if (o.getOrder_type() == 1) {
                        remind_tv.setText("您有电车分时租订单待处理");
                        Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                        if (!remind_re.isShown()) {
                            remind_re.startAnimation(mShowAction);
                            remind_re.setVisibility(View.VISIBLE);
                        }
                        remind_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("6".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, PayActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, BeginUseCarActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("order_state", o.getOrder_state());
                                    startActivity(intent);
                                }
                            }
                        });
                    } else if (o.getOrder_type() == 2) {
                        remind_tv.setText("您有电车长租订单待处理");
                        Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                        if ("1".equals(o.getOrder_state())) {
                            if (!remind_re.isShown()) {
                                remind_re.startAnimation(mShowAction);
                                remind_re.setVisibility(View.VISIBLE);
                            }
                            remind_re.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, ElecInDayEndActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("from", "main");
                                    startActivity(intent);
                                }
                            });

                        } else if ("0".equals(o.getOrder_state())) {
                            Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                            if (!remind_re.isShown()) {
                                remind_re.startAnimation(mShowAction);
                                remind_re.setVisibility(View.VISIBLE);
                            }
                            remind_re.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, ElecExistInDayOrderActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("from", "main");
                                    startActivity(intent);
                                }
                            });
                        }
                    } else if (o.getOrder_type() == 3) {
                        remind_tv.setText("您有油车分时租订单待处理");
                        Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                        if (!remind_re.isShown()) {
                            remind_re.startAnimation(mShowAction);
                            remind_re.setVisibility(View.VISIBLE);
                        }
                        remind_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("6".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, OilPayActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, OilBeginUseCarActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("order_state", o.getOrder_state());
                                    startActivity(intent);
                                }
                            }
                        });
                    } else if (o.getOrder_type() == 4) {
                        remind_tv.setText("您有油车长租订单待处理");
                        Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                        if (!remind_re.isShown()) {
                            remind_re.startAnimation(mShowAction);
                            remind_re.setVisibility(View.VISIBLE);
                        }
                        remind_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, OilInDayEndActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("from", "main");
                                    startActivity(intent);
                                } else if ("0".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, OilExistInDayOrderActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                }
                            }
                        });
                    } else if (o.getOrder_type() == 5) {
                        remind_tv.setText("您有豪车租订单待处理");
                        Glide.with(MainActivity.this).load(o.getCar_image()).into(remind_car_iv);
                        if (!remind_re.isShown()) {
                            remind_re.startAnimation(mShowAction);
                            remind_re.setVisibility(View.VISIBLE);
                        }
                        remind_re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("0".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, LuxuryPreAuditActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    intent.putExtra("from", "main");
                                    startActivity(intent);
                                } else if ("1".equals(o.getOrder_state()) || "9".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, LuxuryConfirmDayTypeActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                } else if ("2".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, LuxuryRentCarActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                } else if ("3".equals(o.getOrder_state())) {
                                    Intent intent = new Intent(MainActivity.this, LuxuryEndActivityV3.class);
                                    intent.putExtra("order_no", o.getOrder_no());
                                    startActivity(intent);
                                }
                            }
                        });

                    } else {
                        if (remind_re.isShown()) {
                            remind_re.startAnimation(mHiddenAction);
                            remind_re.setVisibility(View.GONE);
                        }
                    }

                }
            });
        }

    }


    public void showAdvertisingWindow() {
        adv_dialog = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_pop, null);
        adv_dialog.setContentView(view);
        final Window dialogWindow = adv_dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        adv_dialog.setCanceledOnTouchOutside(false);
        adv_dialog.setCancelable(false);
        dialogWindow.setWindowAnimations(R.style.dialog_anim_style);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.adv_cancel_iv);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adv_dialog.dismiss();
            }
        });
        final PageIndicatorView indicatorView = (PageIndicatorView) view.findViewById(R.id.pageIndicatorView);
        indicatorView.setCount(ad_imageStr_list.size());
        indicatorView.setRadius(5);

        ViewPager ad_vp = (ViewPager) view.findViewById(R.id.ad_vp);
        ad_vp.setAdapter(new AdvertisePageAdapter(this, ad_imageStr_list));
        ad_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adv_dialog.show();
    }

    public void removeAdvDiaLog() {
        adv_dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
