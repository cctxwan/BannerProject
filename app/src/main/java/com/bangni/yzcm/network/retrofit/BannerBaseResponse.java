package com.bangni.yzcm.network.retrofit;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class BannerBaseResponse<T> implements Serializable {

    @SerializedName("code")
    public String code;

    @SerializedName("info")
    public String info;

    @SerializedName("data")
    public T data;
}
