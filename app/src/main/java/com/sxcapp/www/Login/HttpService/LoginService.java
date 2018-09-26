package com.sxcapp.www.Login.HttpService;

import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Login.Bean.LoginResult;
import com.sxcapp.www.Login.Bean.UserId;
import com.sxcapp.www.Util.Properties;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wenleitao on 2017/7/12.
 */

public interface LoginService {
    @POST(Properties.register)
    Observable<Result<UserId>>register(
            @Query("user_name") String user_name,
            @Query("user_code") String user_code,
            @Query("pass_word") String pass_word
    );
    @POST(Properties.sendCode)
    Observable<CodeResultV3<Object>>sendCode(
            @Query("phone_no") String phone,
            @Query("msgType") int msgType

    );
    @POST(Properties.login)
    Observable<CodeResultV3<UserId>>login(
            @Query("phone_no") String phone,
            @Query("phone_code") String user_code

    );
}
