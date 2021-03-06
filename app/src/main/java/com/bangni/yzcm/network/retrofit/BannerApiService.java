package com.bangni.yzcm.network.retrofit;


import com.bangni.yzcm.network.bean.BannerQNiuYModel;
import com.bangni.yzcm.network.bean.ChangeAccountModel;
import com.bangni.yzcm.network.bean.ChangepsdModel;
import com.bangni.yzcm.network.bean.CommunityDWInfos;
import com.bangni.yzcm.network.bean.CommunityInfos;
import com.bangni.yzcm.network.bean.FeedBookListModel;
import com.bangni.yzcm.network.bean.FindPsdBean;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.bean.OrderBannerModel;
import com.bangni.yzcm.network.bean.OrderDetailInfo;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.bean.StatisitcsInfos;
import com.bangni.yzcm.network.bean.UserFeedBookBean;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserGetCodeLoginBean;
import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.bean.UserRegisterBean;
import com.bangni.yzcm.network.util.BannerConstants;

import java.util.List;

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

    //查看用户意见反馈列表
    @POST(BannerConstants.FEEDBOOKLIST_URL)
    Observable<BannerBaseResponse<FeedBookListModel>> getFeedbookLists(@Body RequestBody route);

    //用户个人信息
    @POST(BannerConstants.ACCOUNTINFO_URL)
    Observable<BannerBaseResponse<InfoFragmentBean>> userAccountInfo(@Body RequestBody route);

    //修改用户个人信息
    @POST(BannerConstants.CHANGEACCOUNTINFO_URL)
    Observable<BannerBaseResponse<InfoFragmentBean>> changeUserAccountInfo(@Body RequestBody route);

    //修改用户账号
    @POST(BannerConstants.CHANGEACCOUNT_URL)
    Observable<BannerBaseResponse<ChangeAccountModel>> changeAccount(@Body RequestBody route);

    //订单列表
    @POST(BannerConstants.ORDERLIST_URL)
    Observable<BannerBaseResponse<OrderInfos>> userOrderLists(@Body RequestBody route);

    //修改密码
    @POST(BannerConstants.CHANGEPSD_URL)
    Observable<BannerBaseResponse<ChangepsdModel>> changePsd(@Body RequestBody route);

    //获取社区列表
    @POST(BannerConstants.COMMUNITYLISTALL_URL)
    Observable<BannerBaseResponse<List<CommunityInfos>>> getCommunityLists(@Body RequestBody route);

    //获取点位列表
    @POST(BannerConstants.BROADCASTDWLIST_URL)
    Observable<BannerBaseResponse<List<CommunityDWInfos>>> getDwLists(@Body RequestBody route);

    //获取统计详情
    @POST(BannerConstants.STATISITCSDETAIL_URL)
    Observable<BannerBaseResponse<StatisitcsInfos>> statisitcsDetail(@Body RequestBody route);

    //获取广告详情
    @POST(BannerConstants.ORDERDETAIL_URL)
    Observable<BannerBaseResponse<OrderDetailInfo>> getOrderDetail(@Body RequestBody route);

    //忘记密码
    @POST(BannerConstants.FINDPSD_URL)
    Observable<BannerBaseResponse<FindPsdBean>> findPsd(@Body RequestBody route);

    //获取轮播图
    @POST(BannerConstants.GETBANNER_URL)
    Observable<BannerBaseResponse<OrderBannerModel>> getBannerLists(@Body RequestBody route);

    //获取七牛云凭证
    @POST(BannerConstants.GETQNIUY_URL)
    Observable<BannerBaseResponse<BannerQNiuYModel>> qNiuYtoken();

}
