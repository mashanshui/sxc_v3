package com.sxcapp.www.Download.HttpService;

import com.sxcapp.www.Base.CodeResultV3;
import com.sxcapp.www.Bean.Result;
import com.sxcapp.www.Download.Bean.VersionBean;
import com.sxcapp.www.Util.Properties;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by wenleitao on 2017/8/18.
 */

public interface DownloadService {
    @POST(Properties.getVersions)
    Observable<CodeResultV3<VersionBean>> getVersions();

}
