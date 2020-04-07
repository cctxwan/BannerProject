package com.bangni.yzcm.network.util;

/**
 * 存放网络地址的工具类
 * cc
 */
public class BannerConstants {

    //banner
    public static final String APP_NAME = "banner";

    /** 本地测试 */
    public static final String BASE_URL = "http://192.168.0.188:8870";






    /** 账号密码登录 */
    public static final String LOGIN_URL = "/monitor/user/login";

    /** 账号密码注册 */
    public static final String REGISTER_URL = "/monitor/user/reg";

    /** 验证码登录 */
    public static final String GETCODELOGIN_URL = "/monitor/user/code/login";

    /** 获取验证码 */
    public static final String GETCODE_URL = "/monitor/sys/get_code";

    /** 意见反馈 */
    public static final String FEEDBOOK_URL = "/monitor/user/feedback";

    /** 查看意见反馈列表 */
    public static final String FEEDBOOKLIST_URL = "/monitor/user/get/feedback";

    /** 获取个人信息 */
    public static final String ACCOUNTINFO_URL = "/monitor/user/getinfo";

    /** 修改个人信息 */
    public static final String CHANGEACCOUNTINFO_URL = "/monitor/user/update";

    /** 修改账号 */
    public static final String CHANGEACCOUNT_URL = "/monitor/user/username/update";

    /** 订单列表 */
    public static final String ORDERLIST_URL = "/monitor/order/list";

//    /** 订单列表所有 */
//    public static final String ORDERLISTALL_URL = "/monitor/order/list/all";

    /** 订单详情 */
    public static final String ORDERDETAIL_URL = "/monitor/order/detail";

    /** 播放统计 */
    public static final String PLAYSTATISTICS_URL = "/monitor/order/play/statistics";

//    /** 投放社区列表 */
//    public static final String COMMUNITYLIST_URL = "/monitor/order/community/list";

    /** 投放社区列表所有 */
    public static final String COMMUNITYLISTALL_URL = "/monitor/order/community/list/all";

    /** 监播点位列表 */
    public static final String BROADCASTDWLIST_URL = "/monitor/point/list";

    /** 监播列表 */
    public static final String BROADCASTLIST_URL = "/monitor/list";

    /** 统计详情 */
    public static final String STATISITCSDETAIL_URL = "/monitor/order/statistics/detail";

    /** 监播详情 */
    public static final String BROADCASTDETAILLIST_URL = "/monitor/detail";

    /** 修改密码 */
    public static final String CHANGEPSD_URL = "/monitor/user/pwd/update";

}