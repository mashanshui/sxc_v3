package com.sxcapp.www.UserCenter.EventList;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Base.CodeObserverV3;
import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.R;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.Share.HttpService.ShareService;
import com.sxcapp.www.UserCenter.HttpService.FileUploadService;
import com.sxcapp.www.UserCenter.HttpService.UserCenterService;
import com.sxcapp.www.Util.AndroidTool;
import com.sxcapp.www.Util.FileUtils;
import com.sxcapp.www.Util.ImageTool;
import com.sxcapp.www.Util.SharedData;
import com.sxcapp.www.Util.StatusBarUtil;
import com.sxcapp.www.activity.BaseActivity;
import com.sxcapp.www.webtool.RetrofitManagerV3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by wenleitao on 2018/5/2.
 */

public class UploadSnapShotActivityV3 extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.add_pic06)
    ImageView add_pic06;

    @BindView(R.id.cancel_iv01)
    ImageView cancel_iv01;
    @BindView(R.id.cancel_iv02)
    ImageView cancel_iv02;
    @BindView(R.id.cancel_iv03)
    ImageView cancel_iv03;
    @BindView(R.id.cancel_iv04)
    ImageView cancel_iv04;
    @BindView(R.id.cancel_iv05)
    ImageView cancel_iv05;
    @BindView(R.id.cancel_iv06)
    ImageView cancel_iv06;
    @BindView(R.id.content_re02)
    RelativeLayout content_re02;
    @BindView(R.id.content_re03)
    RelativeLayout content_re03;
    @BindView(R.id.content_edit)
    EditText content_edit;
    @BindView(R.id.content_re)
    RelativeLayout content_re;

    @BindView(R.id.commit_btn)
    Button commit_btn;
    private static final int PIC01_REQUEST = 15, PIC02_REQUEST = 16, PIC03_REQUEST = 17, PIC04_REQUEST = 18, PIC05_REQUEST = 19;
    private static final int PIC06_REQUEST = 20;
    private String pic01_image,
            pic02_image, pic03_image, pic04_image, pic05_image, pic06_image,
            pic01_image_key,
            pic02_image_key, pic03_image_key, pic04_image_key, pic05_image_key, pic06_image_key;
    private int CAMERA_REQUEST;
    private String fileName, theme_id;
    private String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator;
    private FileUploadService service;
    private UserCenterService userCenterService;
    private List<String> image_list, image_key_list, url_list;
    private StringBuffer image_buffer, image_key_buffer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_snapshot_v3);
        setTopbarLeftWhiteBackBtn();
        setStatusView(R.color.top_bar_red);
        setTopBarColor(R.color.top_bar_red);
        StatusBarUtil.StatusBarDarkMode(this);
        setTopBarTitle(getIntent().getStringExtra("theme_tittle"), null);
        ButterKnife.bind(this);

        image_list = new ArrayList<>();
        image_key_list = new ArrayList<>();
        url_list = new ArrayList<>();
        theme_id = getIntent().getStringExtra("theme_id");
        service = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(FileUploadService.class);
        userCenterService = RetrofitManagerV3.RetrofitManagerBuild.INSTANCE.getInstance().creat(UserCenterService.class);
        image_buffer = new StringBuffer();
        image_key_buffer = new StringBuffer();
        bindClick();

    }

    private void bindClick() {
        add_pic01.setOnClickListener(this);
        add_pic02.setOnClickListener(this);
        add_pic03.setOnClickListener(this);
        add_pic04.setOnClickListener(this);
        add_pic05.setOnClickListener(this);
        add_pic06.setOnClickListener(this);
        cancel_iv01.setOnClickListener(this);
        cancel_iv02.setOnClickListener(this);
        cancel_iv03.setOnClickListener(this);
        cancel_iv04.setOnClickListener(this);
        cancel_iv05.setOnClickListener(this);
        cancel_iv06.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
        content_re.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_pic01:
                CAMERA_REQUEST = PIC01_REQUEST;
                getPermission();
                break;
            case R.id.add_pic02:
                CAMERA_REQUEST = PIC02_REQUEST;
                getPermission();

                break;
            case R.id.add_pic03:
                CAMERA_REQUEST = PIC03_REQUEST;
                getPermission();

                break;
            case R.id.add_pic04:
                CAMERA_REQUEST = PIC04_REQUEST;
                getPermission();
                break;
            case R.id.add_pic05:
                CAMERA_REQUEST = PIC05_REQUEST;
                getPermission();
                break;
            case R.id.add_pic06:
                CAMERA_REQUEST = PIC06_REQUEST;
                getPermission();
                break;

            case R.id.commit_btn:
                if (image_list.size() == 0) {
                    showToast("请至少上传一张照片");
                } else if (TextUtils.isEmpty(content_edit.getText().toString().trim())) {
                    showToast("请输入配文");
                } else {
                    uploadCarInfo();
                }
                break;
            case R.id.cancel_iv01:
                image_list.remove(0);
                image_key_list.remove(0);
                url_list.remove(0);
                setBrokenImage();
                break;
            case R.id.cancel_iv02:
                image_list.remove(1);
                image_key_list.remove(1);
                url_list.remove(1);
                setBrokenImage();
                break;
            case R.id.cancel_iv03:
                image_list.remove(2);
                image_key_list.remove(2);
                url_list.remove(2);
                setBrokenImage();
                break;
            case R.id.cancel_iv04:
                image_list.remove(3);
                image_key_list.remove(3);
                url_list.remove(3);
                setBrokenImage();
                break;
            case R.id.cancel_iv05:
                image_list.remove(4);
                image_key_list.remove(4);
                url_list.remove(4);
                setBrokenImage();
                break;
            case R.id.cancel_iv06:
                image_list.remove(5);
                image_key_list.remove(5);
                url_list.remove(5);
                setBrokenImage();
                break;
            case R.id.cancel_iv07:
                image_list.remove(6);
                image_key_list.remove(6);
                url_list.remove(6);
                setBrokenImage();
                break;
            case R.id.cancel_iv08:
                image_list.remove(7);
                image_key_list.remove(7);
                url_list.remove(7);
                setBrokenImage();
                break;
            case R.id.content_re:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            default:
                break;
        }
    }

    private void uploadCarInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("customer_id", SharedData.getInstance().getString(SharedData._user_id));
        map.put("theme_id", theme_id);
        for (int i = 0; i < image_list.size(); i++) {
            if (i == 0) {
                image_buffer.append(image_list.get(i));
            } else {
                image_buffer.append("," + image_list.get(i));
            }
        }

        for (int i = 0; i < image_key_list.size(); i++) {
            if (i == 0) {
                image_key_buffer.append(image_key_list.get(i));
            } else {
                image_key_buffer.append("," + image_key_list.get(i));
            }
        }
        map.put("pose_image", image_buffer.toString());
        map.put("pose_image_view", image_key_buffer.toString());
        map.put("content", content_edit.getText().toString().trim());
        showProgressDlg();
        Observable<CodeResultV3<Object>> ob = userCenterService.snapshotUploadInfoV3(map);
        ob.compose(compose(this.<CodeResultV3<Object>>bindToLifecycle())).subscribe(new CodeObserverV3<Object>(this) {
            @Override
            protected void onHandleSuccess(Object o) {
                removeProgressDlg();
                showToast("参与成功,敬请期待开奖结果");
                setResult(RESULT_OK);
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
            try {
                Uri u = Uri.parse(MediaStore.Images.Media
                        .insertImage(getContentResolver(),
                                path + fileName, null, null));
                Bitmap bitmap = ImageTool.decodeUriAsBitmap(u, this);

                if (bitmap != null) {
                    String imgPath = ImageTool.amendRotatePhoto(path + fileName, bitmap, true);
                    Luban.with(this)
                            .load(imgPath)
                            .ignoreBy(100)
                            .setTargetDir(ImageTool.getPath())
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File file) {
                                    uploadImage(requestCode, file.getAbsolutePath());
                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            }).launch();
                    deleteImage();
                    bitmap.recycle();
                    System.gc();
                    deleteImage();
                    bitmap.recycle();
                    System.gc();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }


    public void deleteImage() {
        File file = new File(path + fileName);
        file.delete();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = UploadSnapShotActivityV3.this.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + fileName + "'";
//删除图片
        mContentResolver.delete(mImageUri, where, null);
//发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        UploadSnapShotActivityV3.this.sendBroadcast(intent);
    }

    public void uploadImage(final int code, final String url) {
        showProgressDlg();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpg"), new File(url));
        Observable<CodeResultV3<ImageBeanV3>> observable = service.uploadImageV3("sharePose", requestFile);
        observable.compose(compose(this.<CodeResultV3<ImageBeanV3>>bindToLifecycle())).subscribe(new CodeObserverV3<ImageBeanV3>(this) {
            @Override
            protected void onHandleSuccess(ImageBeanV3 imageBean) {
                removeProgressDlg();
                switch (code) {
                    case PIC01_REQUEST:
                        pic01_image = imageBean.getSave_path();
                        pic01_image_key = imageBean.getView_path();
                        image_list.add(pic01_image);
                        image_key_list.add(pic01_image_key);
                        url_list.add(url);
                        setBrokenImage();
                        break;
                    case PIC02_REQUEST:
                        pic02_image = imageBean.getSave_path();
//                        broken_image.append("," + pic02_image);
                        pic02_image_key = imageBean.getView_path();
//                        broken_image_key.append("," + pic02_image_key);
                        image_list.add(pic02_image);
                        image_key_list.add(pic02_image_key);
//                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(add_pic02);
                        url_list.add(url);
//                        cance_iv02.setVisibility(View.VISIBLE);
//                        add_pic_re.setVisibility(View.VISIBLE);
//                        add_pic03.setVisibility(View.VISIBLE);
                        setBrokenImage();
                        break;
                    case PIC03_REQUEST:
                        pic03_image = imageBean.getSave_path();
//                        broken_image.append("," + pic03_image);
                        pic03_image_key = imageBean.getView_path();
//                        broken_image_key.append("," + pic03_image_key);
                        image_list.add(pic03_image);
                        image_key_list.add(pic03_image_key);
//                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(add_pic03);
                        url_list.add(url);
//                        cance_iv03.setVisibility(View.VISIBLE);
//                        add_pic04.setVisibility(View.VISIBLE);
                        setBrokenImage();
                        break;
                    case PIC04_REQUEST:
                        pic04_image = imageBean.getSave_path();
//                        broken_image.append("," + pic04_image);
                        pic04_image_key = imageBean.getView_path();
//                        broken_image_key.append("," + pic04_image_key);
                        image_list.add(pic04_image);
                        image_key_list.add(pic04_image_key);
//                        cance_iv04.setVisibility(View.VISIBLE);
//                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(add_pic04);
                        url_list.add(url);
//                        add_re05.setVisibility(View.VISIBLE);
//                        add_pic05.setVisibility(View.VISIBLE);
                        setBrokenImage();
                        break;
                    case PIC05_REQUEST:
                        pic05_image = imageBean.getSave_path();
//                        broken_image.append("," + pic05_image);
                        pic05_image_key = imageBean.getView_path();
//                        broken_image_key.append("," + pic05_image_key);
                        image_list.add(pic05_image);
                        image_key_list.add(pic05_image_key);
                        url_list.add(url);
//                        cance_iv05.setVisibility(View.VISIBLE);
//                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(add_pic05);
                        setBrokenImage();
                        break;
                    case PIC06_REQUEST:
                        pic06_image = imageBean.getSave_path();
//                        broken_image.append("," + pic05_image);
                        pic06_image_key = imageBean.getView_path();
//                        broken_image_key.append("," + pic05_image_key);
                        image_list.add(pic06_image);
                        image_key_list.add(pic06_image_key);
                        url_list.add(url);
//                        cance_iv05.setVisibility(View.VISIBLE);
//                        Glide.with(UploadCarInfoActivityV3.this).load(url).into(add_pic05);
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
                showToast("上传失败");
            }
        });
    }

    private void setBrokenImage() {
        switch (url_list.size()) {
            case 0:
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic01);
                cancel_iv01.setVisibility(View.INVISIBLE);
                add_pic02.setVisibility(View.INVISIBLE);
                cancel_iv02.setVisibility(View.INVISIBLE);
                content_re02.setVisibility(View.GONE);
                content_re03.setVisibility(View.GONE);
                add_pic01.setClickable(true);
                add_pic02.setClickable(false);

                break;
            case 1:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic02);
                add_pic02.setVisibility(View.VISIBLE);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.INVISIBLE);
                content_re02.setVisibility(View.GONE);
                content_re03.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(true);
                break;
            case 2:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic03);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.INVISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.INVISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(true);
                add_pic04.setClickable(false);
                break;
            case 3:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic04);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.INVISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.GONE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(false);
                add_pic04.setClickable(true);
                add_pic05.setClickable(false);
                break;
            case 4:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic05);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                add_pic06.setVisibility(View.INVISIBLE);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.VISIBLE);
                cancel_iv05.setVisibility(View.INVISIBLE);
                cancel_iv06.setVisibility(View.INVISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.VISIBLE);
                add_pic01.setClickable(false);
                add_pic02.setClickable(false);
                add_pic03.setClickable(false);
                add_pic04.setClickable(false);
                add_pic05.setClickable(true);
                break;
            case 5:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(url_list.get(4)).apply(new RequestOptions().centerCrop()).into(add_pic05);
                Glide.with(this).load(R.mipmap.add_pic_v3).into(add_pic06);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.VISIBLE);
                cancel_iv05.setVisibility(View.VISIBLE);
                cancel_iv06.setVisibility(View.INVISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                add_pic06.setVisibility(View.VISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.VISIBLE);
                break;
            case 6:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(url_list.get(4)).apply(new RequestOptions().centerCrop()).into(add_pic05);
                Glide.with(this).load(url_list.get(5)).apply(new RequestOptions().centerCrop()).into(add_pic06);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.VISIBLE);
                cancel_iv05.setVisibility(View.VISIBLE);
                cancel_iv06.setVisibility(View.VISIBLE);
                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                add_pic06.setVisibility(View.VISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.VISIBLE);

                break;
            case 7:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(url_list.get(4)).apply(new RequestOptions().centerCrop()).into(add_pic05);
                Glide.with(this).load(url_list.get(5)).apply(new RequestOptions().centerCrop()).into(add_pic06);

                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.VISIBLE);
                cancel_iv05.setVisibility(View.VISIBLE);
                cancel_iv06.setVisibility(View.VISIBLE);

                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                add_pic06.setVisibility(View.VISIBLE);

                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.VISIBLE);

                break;
            case 8:
                Glide.with(this).load(url_list.get(0)).apply(new RequestOptions().centerCrop()).into(add_pic01);
                Glide.with(this).load(url_list.get(1)).apply(new RequestOptions().centerCrop()).into(add_pic02);
                Glide.with(this).load(url_list.get(2)).apply(new RequestOptions().centerCrop()).into(add_pic03);
                Glide.with(this).load(url_list.get(3)).apply(new RequestOptions().centerCrop()).into(add_pic04);
                Glide.with(this).load(url_list.get(4)).apply(new RequestOptions().centerCrop()).into(add_pic05);
                Glide.with(this).load(url_list.get(5)).apply(new RequestOptions().centerCrop()).into(add_pic06);
                cancel_iv01.setVisibility(View.VISIBLE);
                cancel_iv02.setVisibility(View.VISIBLE);
                cancel_iv03.setVisibility(View.VISIBLE);
                cancel_iv04.setVisibility(View.VISIBLE);
                cancel_iv05.setVisibility(View.VISIBLE);
                cancel_iv06.setVisibility(View.VISIBLE);

                add_pic02.setVisibility(View.VISIBLE);
                add_pic03.setVisibility(View.VISIBLE);
                add_pic04.setVisibility(View.VISIBLE);
                add_pic05.setVisibility(View.VISIBLE);
                add_pic06.setVisibility(View.VISIBLE);
                content_re02.setVisibility(View.VISIBLE);
                content_re03.setVisibility(View.VISIBLE);
                break;
            default:
                break;


        }

    }

    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionGen.with(UploadSnapShotActivityV3.this)
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
