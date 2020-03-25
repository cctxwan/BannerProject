package com.bangni.yzcm.network.retrofit;


import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.util.BannerConstants;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Api请求管理
 */
public interface BannerApiService {

    //用户账号密码登录
    @POST(BannerConstants.LOGIN_URL)
    Observable<BannerBaseResponse<UserLoginBean>> userLogin(@Body RequestBody route);

}
