package com.bangni.yzcm.network.bean;

/**
 * 广告订单详情
 */
public class OrderDetailInfo {
    /**
     * avgNumber : 0
     * avgPlay : 0
     * gpuDiscernResult : null
     * monitorImage :
     * monitorTime : null
     * pid : 0
     * pointPid : 0
     * putCommunityPid : 0
     * status : 0
     */

    private int avgNumber;
    private int avgPlay;
    private Object gpuDiscernResult;
    private String monitorImage;
    private Object monitorTime;
    private int pid;
    private int pointPid;
    private int putCommunityPid;
    private int status;

    public int getAvgNumber() {
        return avgNumber;
    }

    public void setAvgNumber(int avgNumber) {
        this.avgNumber = avgNumber;
    }

    public int getAvgPlay() {
        return avgPlay;
    }

    public void setAvgPlay(int avgPlay) {
        this.avgPlay = avgPlay;
    }

    public Object getGpuDiscernResult() {
        return gpuDiscernResult;
    }

    public void setGpuDiscernResult(Object gpuDiscernResult) {
        this.gpuDiscernResult = gpuDiscernResult;
    }

    public String getMonitorImage() {
        return monitorImage;
    }

    public void setMonitorImage(String monitorImage) {
        this.monitorImage = monitorImage;
    }

    public Object getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Object monitorTime) {
        this.monitorTime = monitorTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPointPid() {
        return pointPid;
    }

    public void setPointPid(int pointPid) {
        this.pointPid = pointPid;
    }

    public int getPutCommunityPid() {
        return putCommunityPid;
    }

    public void setPutCommunityPid(int putCommunityPid) {
        this.putCommunityPid = putCommunityPid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
