package com.bangni.yzcm.network.retrofit;

/**
 * 既然我们只关注onNext数据，所以把它提取出来，做成一个接口，
 * 以便于我们在Activity或者fragment中对数据进行操作，
 * 由于我们数据类型未知，所以这里也传入一个泛型。
 * Created by Fsh on 2017/3/16.
 */
public interface BannerSubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(String msg);
}
