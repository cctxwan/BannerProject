package com.bangni.yzcm.network.bean;

/**
 * 上传七牛云凭证
 */
public class BannerQNiuYModel {

    private String uploadToken;

    private String uploadKey;


    public String getUploadToken() {
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
    }

    public String getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
    }

}
