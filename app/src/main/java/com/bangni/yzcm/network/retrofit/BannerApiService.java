package com.bangni.yzcm.network.retrofit;


import com.bangni.yzcm.network.bean.UserFeedBookBean;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserGetCodeLoginBean;
import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.bean.UserRegisterBean;
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

    //用户账号密码注册
    @POST(BannerConstants.REGISTER_URL)
    Observable<BannerBaseResponse<UserRegisterBean>> userRegister(@Body RequestBody route);

    //用户账号密码注册
    @POST(BannerConstants.GETCODELOGIN_URL)
    Observable<BannerBaseResponse<UserGetCodeLoginBean>> userGetCodeLogin(@Body RequestBody route);

    //用户获取验证码
    @POST(BannerConstants.GETCODE_URL)
    Observable<BannerBaseResponse<UserGetCodeBean>> userGetCode(@Body RequestBody route);

    //用户意见反馈
    @POST(BannerConstants.FEEDBOOK_URL)
    Observable<BannerBaseResponse<UserFeedBookBean>> userFeedBook(@Body RequestBody route);

}
