package com.bangni.yzcm.network.bean;

/**
 * 广告订单详情
 */
public class OrderDetailInfo {
    /**
     * avgNumber : 0
     * avgPlay : 380
     * gpuDiscernResult : {}
     * monitorImage : http://images.81dd.cn/376c449b2b2047cdbdf5c22ca94d957a
     * monitorTime : 1586573574000
     * pid : 53
     * pointPid : 64
     * putCommunityPid : 20
     * status : 2
     */

    private int avgNumber;
    private int avgPlay;
    private GpuDiscernResultBean gpuDiscernResult;
    private String monitorImage;
    private long monitorTime;
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

    public GpuDiscernResultBean getGpuDiscernResult() {
        return gpuDiscernResult;
    }

    public void setGpuDiscernResult(GpuDiscernResultBean gpuDiscernResult) {
        this.gpuDiscernResult = gpuDiscernResult;
    }

    public String getMonitorImage() {
        return monitorImage;
    }

    public void setMonitorImage(String monitorImage) {
        this.monitorImage = monitorImage;
    }

    public long getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(long monitorTime) {
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

    public static class GpuDiscernResultBean {
    }
}
