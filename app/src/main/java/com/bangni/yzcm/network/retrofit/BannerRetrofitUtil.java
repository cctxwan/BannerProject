package com.bangni.yzcm.network.retrofit;

import android.util.Log;
import com.bangni.yzcm.app.BannerApplication;
import com.bangni.yzcm.network.bean.ChangepsdModel;
import com.bangni.yzcm.network.bean.FeedBookListModel;
import com.bangni.yzcm.network.bean.InfoFragmentBean;
import com.bangni.yzcm.network.bean.OrderInfos;
import com.bangni.yzcm.network.bean.UserFeedBookBean;
import com.bangni.yzcm.network.bean.UserGetCodeBean;
import com.bangni.yzcm.network.bean.UserGetCodeLoginBean;
import com.bangni.yzcm.network.bean.UserLoginBean;
import com.bangni.yzcm.network.bean.UserRegisterBean;
import com.bangni.yzcm.network.util.BannerConstants;
import com.bangni.yzcm.utils.BannerLog;
import com.bangni.yzcm.utils.BannerPreferenceStorage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by Fsh on 2017/9/5.
 */

public class BannerRetrofitUtil {

    public static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private BannerApiService mApiService;


    public static BannerRetrofitUtil getInstance() {
        return Nested.instance;
    }

    //在第一次被引用时被加载
    static class Nested {
        private static BannerRetrofitUtil instance = new BannerRetrofitUtil();
    }

    /**
     * 私有构造方法
     */
    private BannerRetrofitUtil() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.d("b_cc", "--->" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-type", "application/json;charset=UTF-8")
                        .header("token", new BannerPreferenceStorage(BannerApplication.getInstance()).getToken())
                        .method(original.method(), original.body())
                        .build();
                printParams(request.body());
                return chain.proceed(request);
            }
        });
        okHttpClient.addInterceptor(loggingInterceptor);
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//增加返回值为Gson的支持(以实体类返回)
                .baseUrl(BannerConstants.BASE_URL)//主机地址
                .client(okHttpClient.build())//注意这里要给retrofit 设置okhttpclient
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(BannerApiService.class);
    }

    /**
     * 输出请求参数
     * @param body
     */
    private void printParams(RequestBody body) {
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }
            String params = buffer.readString(charset);
            BannerLog.d("b_cc", "请求参数为--->" + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())//事件产生的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//事件消费的线程
                .subscribe(subscriber);
    }




    //用户账号密码登录
    public void userLogin(@Body RequestBody route, Subscriber<BannerBaseResponse<UserLoginBean>> subscriber) {
        toSubscribe(mApiService.userLogin(route), subscriber);
    }

    //用户账号密码注册
    public void userRegister(@Body RequestBody route, Subscriber<BannerBaseResponse<UserRegisterBean>> subscriber) {
        toSubscribe(mApiService.userRegister(route), subscriber);
    }

    //用户手机号登录
    public void userGetCodeLogin(@Body RequestBody route, Subscriber<BannerBaseResponse<UserGetCodeLoginBean>> subscriber) {
        toSubscribe(mApiService.userGetCodeLogin(route), subscriber);
    }

    //用户获取验证码
    public void userGetCode(@Body RequestBody route, Subscriber<BannerBaseResponse<UserGetCodeBean>> subscriber) {
        toSubscribe(mApiService.userGetCode(route), subscriber);
    }

    //用户意见反馈
    public void userFeedBook(@Body RequestBody route, Subscriber<BannerBaseResponse<UserFeedBookBean>> subscriber) {
        toSubscribe(mApiService.userFeedBook(route), subscriber);
    }

    //查看用户意见反馈列表
    public void getFeedbookLists(@Body RequestBody route, Subscriber<BannerBaseResponse<FeedBookListModel>> subscriber) {
        toSubscribe(mApiService.getFeedbookLists(route), subscriber);
    }

    //用户信息
    public void userAccountInfo(Subscriber<BannerBaseResponse<InfoFragmentBean>> subscriber) {
        toSubscribe(mApiService.userAccountInfo(), subscriber);
    }

    //订单列表
    public void userOrderLists(@Body RequestBody route, Subscriber<BannerBaseResponse<OrderInfos>> subscriber) {
        toSubscribe(mApiService.userOrderLists(route), subscriber);
    }

    //修改密码
    public void changePsd(@Body RequestBody route, Subscriber<BannerBaseResponse<ChangepsdModel>> subscriber) {
        toSubscribe(mApiService.changePsd(route), subscriber);
    }

}
