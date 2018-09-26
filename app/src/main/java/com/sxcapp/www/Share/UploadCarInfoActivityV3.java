package com.sxcapp.www.Share;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 用车前后上传车况照片
 * Created by wenleitao on 2017/9/16.
 */

public class UploadCarInfoActivityV3 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.right_image_iv)
    ImageView right_image_iv;
    @BindView(R.id.left_image_iv)
    ImageView left_image_iv;
    @BindView(R.id.driverseat_image_iv)
    ImageView driverseat_image_iv;
    @BindView(R.id.behind_image_iv)
    ImageView behind_image_iv;
    @BindView(R.id.commit_btn)
    Button commit_btn;
    @BindView(R.id.add_pic01)
    ImageView add_pic01;
    @BindView(R.id.add_pic02)
    ImageView add_pic02;
    @BindView(R.id.add_pic03)
    ImageView add_pic03;
    @BindView(R.id.add_pic04)
    ImageView add_pic04;
    @BindView(R.id.add_pic05)
    ImageView add_pic05;
    @BindView(R.id.add_pic_re)
    RelativeLayout add_pic_re;
    @BindView(R.id.cancel_iv01)
    ImageView cance_iv01;
    @BindView(R.id.cancel_iv02)
    ImageView cance_iv02;
    @BindView(R.id.cancel_iv03)
    ImageView cance_iv03;
    @BindView(R.id.cancel_iv04)
    ImageView cance_iv04;
    @BindView(R.id.cancel_iv05)
    ImageView cance_iv05;
    @BindView(R.id.add_re05)
    RelativeLayout add_re05;
    @BindView(R.id.take_photo_iv01)
    ImageView take_photo_iv01;
    @BindView(R.id.take_photo_iv02)
    ImageView take_photo_iv02;
    @BindView(R.id.take_photo_iv03)
    ImageView take_photo_iv03;
    @BindView(R.id.take_photo_iv04)
    ImageView take_photo_iv04;
    @BindView(R.id.luxury_remind_tv)
    TextView luxury_remind_tv;
    private String fileName;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private static final int RIGHT_REQUEST = 11, LEFT_REQUEST = 12, DRIVERSEAT_REQUEST = 13, BEHIND_REQUEST = 14;
    private static final int PIC01_REQUEST = 15, PIC02_REQUEST = 16, PIC03_REQUEST = 17, PIC04_REQUEST = 18, PIC05_REQUEST = 19;
    private FileUploadService service;
    private ShareService shareService;
    private String right_image, left_image, driverseat_image, behind_image, order_no, car_id, pic01_image,
            pic02_image, pic03_image, pic04_image, pic05_image, upload_type;
    private String right_image_key, left_image_key, driverseat_image_key, behind_image_key, pic01_image_key,
            pic02_image_key, pic03_image_key, pic04_image_key, pic05_image_key;
    private StringBuffer broken_image, broken_image_key;
    private int type, order_type;
    private int CAMERA_REQUEST;
    private List<String> broken_image_list, broken_image_key_list, broken_url_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_carinfo_v3);
        setTopbarLeftWhiteBackBtn();
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle("反馈车况", null);
        ButterKnife.bind(this);
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        shareService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(ShareService.class);
        right_image_iv.setOnClickListener(this);
        left_image_iv.setOnClickListener(this);
        driverseat_image_iv.setOnClickListener(this);
        behind_image_iv.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
        add_pic01.setOnClickListener(this);
        add_pic02.setOnClickListener(this);
        add_pic03.setOnClickListener(this);
        add_pic04.setOnClickListener(this);
        add_pic05.setOnClickListener(this);
        cance_iv01.setOnClickListener(this);
        cance_iv02.setOnClickListener(this);
        cance_iv03.setOnClickListener(this);
        cance_iv04.setOnClickListener(this);
        cance_iv05.setOnClickListener(this);
        broken_image_list = new ArrayList<>();
        broken_image_key_list = new ArrayList<>();
        broken_url_list = new ArrayList<>();
        broken_image = new StringBuffer();
        broken_image_key = new StringBuffer();
        order_no = getIntent().getStringExtra("order_no");
        car_id = getIntent().getStringExtra("car_id");
        type = getIntent().getIntExtra("type", 0);
        order_type = getIntent().getIntExtra("order_type", 0);
        if (order_type == 1 || order_type == 2) {
            setStatusView(R.color.green);
            setTopBarColor(R.color.green);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_green);
        } else if (order_type == 3 || order_type == 4) {
            setStatusView(R.color.top_bar_red);
            setTopBarColor(R.color.top_bar_red);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_red);
        } else if (order_type == 5) {
            setStatusView(R.color.luxury);
            setTopBarColor(R.color.luxury);
            commit_btn.setBackgroundResource(R.drawable.selector_btn_click_bg_luxury);
            if (type == 1) {
                luxury_remind_tv.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_image_iv:
                CAMERA_REQUEST = RIGHT_REQUEST;
                upload_type = "shareOrderAudit";
                getPermission();
                break;
            case R.id.left_image_iv:
                CAMERA_REQUEST = LEFT_REQUEST;
                upload_type = "shareOrderAudit";
                getPermission();
                break;
            case R.id.driverseat_image_iv:
                CAMERA_REQUEST = DRIVERSEAT_REQUEST;
                upload_type = "shareOrderAudit";
                getPermission();
                break;
            case R.id.behind_image_iv:
                CAMERA_REQUEST = BEHIND_REQUEST;
                upload_type = "shareOrderAudit";
                getPermission();
                break;
            case R.id.add_pic01:
                CAMERA_REQUEST = PIC01_REQUEST;
                upload_type = "shareCarBroken";
                getPermission();
                break;
            case R.id.add_pic02:
                CAMERA_REQUEST = PIC02_REQUEST;
                upload_type = "shareCarBroken";
                getPermission();

                break;
            case R.id.add_pic03:
                CAMERA_REQUEST = PIC03_REQUEST;
                upload_type = "shareCarBroken";
                getPermission();

                break;
            case R.id.add_pic04:
                CAMERA_REQUEST = PIC04_REQUEST;
                upload_type = "shareCarBroken";
                getPermission();
                break;
            case R.id.add_pic05:
                CAMERA_REQUEST = PIC05_REQUEST;
                upload_type = "shareCarBroken";
                getPermission();

                break;
            case R.id.commit_btn:
                if (TextUtils.isEmpty(driverseat_image)) {
                    showToast("请上传车内前排照片");
                    return;
                } else if (TextUtils.isEmpty(behind_image)) {
                    showToast("请上传车身后外侧照片");
                    return;
                } else if (TextUtils.isEmpty(left_image)) {
                    showToast("请上传45度角车身左前侧照片");
                    return;
                } else if (TextUtils.isEmpty(right_image)) {
                    showToast("请上传45度角车身右前侧照片");
                    return;
                } else {
                    if (order_type == 5 && type == 1) {
                        if (broken_image_list.size() > 0) {
                            uploadCarInfo();
                        } else {
                            showToast("请上传验车单图片");
                        }
                    } else {
                        uploadCarInfo();
                    }
                }
                break;
            case R.id.cancel_iv01:
                broken_image_list.remove(0);
                broken_image_key_list.remove(0);
                broken_url_list.remove(0);
                setBrokenImage();
                break;
            case R.id.cancel_iv02:
                broken_image_list.remove(1);
                broken_image_key_list.remove(1);
                broken_url_list.remove(1);
                setBrokenImage();
                break;
            case R.id.cancel_iv03:
                broken_image_list.remove(2);
                broken_image_key_list.remove(2);
                broken_url_list.remove(2);
                setBrokenImage();
                break;
            case R.id.cancel_iv04:
                broken_image_list.remove(3);
                broken_image_key_list.remove(3);
                broken_url_list.remove(3);
                setBrokenImage();
                break;
            case R.id.cancel_iv05:
                broken_image_list.remove(4);
                broken_image_key_list.remove(4);
                broken_url_list.remove(4);
                setBrokenImage();
                break;
            default:
                break;
        }
    }

    private void setBrokenImage() {
        switch (broken_url_list.size()) {
            case 0:
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic01);
                cance_iv01.setVisibility(View.INVISIBLE);
                add_pic02.setVisibility(View.INVISIBLE);
                cance_iv02.setVisibility(View.INVISIBLE);
                add_pic_re.setVisibility(View.GONE);
                add_re05.setVisibility(View.GONE);
                add_pic01.setClickable(true);
                add_pic02.setClickable(false);
                add_pic03.setClickable(false);
                add_pic04.setClickable(false);
                add_pic05.setClickable(false);
                break;
            case 1:
                Glide.with(this).load(broken_url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic02);
                add_pic02.setVisibility(View.VISIBLE);
                cance_iv01.setVisibility(View.VISIBLE);
                cance_iv02.setVisibility(View.INVISIBLE);
                add_pic_re.setVisibility(View.GONE);
                add_re05.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(true);
                add_pic03.setClickable(false);
                add_pic04.setClickable(false);
                add_pic05.setClickable(false);
                break;
            case 2:
                Glide.with(this).load(broken_url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(broken_url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                cance_iv01.setVisibility(View.VISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                cance_iv02.setVisibility(View.VISIBLE);
                add_pic_re.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic03);
                cance_iv03.setVisibility(View.INVISIBLE);
                add_pic04.setVisibility(View.INVISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                cance_iv04.setVisibility(View.INVISIBLE);
                add_re05.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(true);
                add_pic04.setClickable(false);
                add_pic05.setClickable(false);
                break;
            case 3:
                Glide.with(this).load(broken_url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(broken_url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(broken_url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                cance_iv01.setVisibility(View.VISIBLE);
                cance_iv02.setVisibility(View.VISIBLE);
                cance_iv03.setVisibility(View.VISIBLE);
                add_pic_re.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic04);
                cance_iv04.setVisibility(View.INVISIBLE);
                add_re05.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(false);
                add_pic04.setClickable(true);
                add_pic05.setClickable(false);
                break;
            case 4:
                Glide.with(this).load(broken_url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(broken_url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(broken_url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(broken_url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                cance_iv01.setVisibility(View.VISIBLE);
                cance_iv02.setVisibility(View.VISIBLE);
                cance_iv03.setVisibility(View.VISIBLE);
                add_pic_re.setVisibility(View.VISIBLE);
                cance_iv04.setVisibility(View.VISIBLE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(false);
                add_pic04.setClickable(false);
                add_pic05.setClickable(true);
                add_re05.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic05);
                cance_iv05.setVisibility(View.INVISIBLE);
                break;
            case 5:
                Glide.with(this).load(broken_url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(broken_url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(broken_url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(broken_url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(broken_url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic05);
                cance_iv01.setVisibility(View.VISIBLE);
                cance_iv02.setVisibility(View.VISIBLE);
                cance_iv03.setVisibility(View.VISIBLE);
                add_pic_re.setVisibility(View.VISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                cance_iv04.setVisibility(View.VISIBLE);
                add_re05.setVisibility(View.VISIBLE);
                cance_iv05.setVisibility(View.VISIBLE);
                break;
            default:
                break;


        }

    }

    private void uploadCarInfo() {
        showProgressDlg();
        if (broken_image_list.size() > 0) {
            for (int i = 0; i < broken_image_list.size(); i++) {
                if (i == 0) {
                    broken_image.append(broken_image_list.get(i));
                    broken_image_key.append(broken_image_key_list.get(i));
                } else {
                    broken_image.append("," + broken_image_list.get(i));
                    broken_image_key.append("," + broken_image_key_list.get(i));
                }
            }
        }
        Observable<CodeResultV3<Object>> ob = shareService.orderAuditImageV3(SharedData.getInstance().getString(SharedData._user_id), order_no, car_id, right_image
                , left_image, driverseat_image, behind_image, right_image_key
                , left_image_key, driverseat_image_key, behind_image_key, broken_image.toString(), broken_image_key.toString(), type, order_type);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("上传成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }


    public void goCamera(int requestcode) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        if (Build.VERSION.SDK_INT > 23) {
            FileUtils.startActionCapture(this, new File(path + fileName), requestcode);

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, requestcode);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Luban.with(this)
                    .load(path + fileName)
                    .ignoreBy(100)
                    .setTargetDir(ImageTool.getLubanPath())
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadImage(requestCode, file.getAbsolutePath());
                            ImageTool.deleteImage(path + fileName, UploadCarInfoActivityV3.this);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e("照片压缩异常" + e.toString());
                        }
                    }).launch();


        }
    }


    public void uploadImage(final int code, final String url) {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(url));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3(upload_type, requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                removeProgressDlg();
                switch (code) {
                    case RIGHT_REQUEST:
                        right_image = imageBean.getSave_path();
                        right_image_key = imageBean.getView_path();
                        take_photo_iv04.setVisibility(View.GONE);
                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(right_image_iv);
                        break;
                    case LEFT_REQUEST:
                        left_image = imageBean.getSave_path();
                        left_image_key = imageBean.getView_path();
                        take_photo_iv03.setVisibility(View.GONE);
                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(left_image_iv);
                        break;
                    case DRIVERSEAT_REQUEST:
                        driverseat_image = imageBean.getSave_path();
                        take_photo_iv01.setVisibility(View.GONE);
                        driverseat_image_key = imageBean.getView_path();
                        take_photo_iv01.setVisibility(View.GONE);
                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(driverseat_image_iv);
                        break;
                    case BEHIND_REQUEST:
                        take_photo_iv02.setVisibility(View.GONE);
                        behind_image = imageBean.getSave_path();
                        behind_image_key = imageBean.getView_path();
                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(behind_image_iv);
                        break;
                    case PIC01_REQUEST:
                        pic01_image = imageBean.getSave_path();
                        pic01_image_key = imageBean.getView_path();
                        broken_image_list.add(pic01_image);
                        broken_image_key_list.add(pic01_image_key);
                        broken_url_list.add(url);
                        setBrokenImage();
                        break;
                    case PIC02_REQUEST:
                        pic02_image = imageBean.getSave_path();
                        pic02_image_key = imageBean.getView_path();
                        broken_image_list.add(pic02_image);
                        broken_image_key_list.add(pic02_image_key);
                        broken_url_list.add(url);
                        setBrokenImage();
                        break;
                    case PIC03_REQUEST:
                        pic03_image = imageBean.getSave_path();

                        pic03_image_key = imageBean.getView_path();
                        broken_image_list.add(pic03_image);
                        broken_image_key_list.add(pic03_image_key);
                        broken_url_list.add(url);
                        setBrokenImage();
                        break;
                    case PIC04_REQUEST:
                        pic04_image = imageBean.getSave_path();
                        pic04_image_key = imageBean.getView_path();
                        broken_image_list.add(pic04_image);
                        broken_image_key_list.add(pic04_image_key);
                        broken_url_list.add(url);
                        setBrokenImage();
                        break;
                    case PIC05_REQUEST:
                        pic05_image = imageBean.getSave_path();
                        pic05_image_key = imageBean.getView_path();
                        broken_image_list.add(pic05_image);
                        broken_image_key_list.add(pic05_image_key);
                        broken_url_list.add(url);
                        setBrokenImage();
                        break;
                    default:
                        break;
                }
            }

            @Override
            protected void onHandleError(String msg, CodeResultV3<ImageBeanV3> value) {
                super.onHandleError(msg, value);
            }

            @Override
            public void onError(Throwable e) {
                removeProgressDlg();
                super.onError(e);
                Logger.e(e.toString());
                showToast("上传失败");
            }
        });
    }


    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(UploadCarInfoActivityV3.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .request();
        } else {
            goCamera(CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        goCamera(CAMERA_REQUEST);
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        showAlertDlg("使用拍照功能，请开启拍照和读取手机储存权限", R.string.set, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }, R.string.refuse, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlertDlg();
            }
        }, true);
    }
}
