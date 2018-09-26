package com.sxcapp.www.UserCenter.HttpService;

import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Share.Bean.ImageBeanV3;
import com.sxcapp.www.UserCenter.Bean.ImageBean;
import com.sxcapp.www.Util.Properties;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by wenleitao on 2017/7/28.
 */

public interface FileUploadService {
    @Multipart
    @POST(Properties.uploadImage)
    Observable<Result<ImageBean>> uploadImage(
            @Query("userId") String userId,
            @Part("pic\"; filename=\"image.jpg") RequestBody imgs1
    );
    @Multipart
    @POST(Properties.uploadImageAuth)
    Observable<Result<ImageBean>> uploadImageAuth(
            @Query("userId") String userId,
            @Part("pic\"; filename=\"image.jpg") RequestBody imgs1
    );

    @Multipart
    @POST(Properties.uploadCrash)
    Observable<Result<Object>> uploadCrash(
            @Part("text\"; filename=\"crash.txt") RequestBody file1
    );
    @Multipart
    @POST(Properties.uploadCarImage)
    Observable<Result<ImageBean>> uploadCarImage(
            @Part("pic\"; filename=\"image.jpg") RequestBody imgs1
    );
    @Multipart
    @POST(Properties.uploadProblemImage)
    Observable<Result<ImageBean>> uploadProblemImage(
            @Part("pic\"; filename=\"image.jpg") RequestBody imgs1
    );
    @Multipart
    @POST(Properties.uploadImageV3)
    Observable<CodeResultV3<ImageBeanV3>>uploadImageV3(
            @Query("type") String type,
            @Part("pic\"; filename=\"image.jpg") RequestBody imgs1
    );

}
