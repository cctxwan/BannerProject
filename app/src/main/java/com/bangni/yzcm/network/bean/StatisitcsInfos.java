package com.bangni.yzcm.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class StatisitcsInfos {
    /**
     * communityName : 辉少
     * cumulativeDischarge : {"12-31":633,"1-1":1744,"1-2":84}
     * cumulativePlay : null
     * pointNum : 0
     */

    private String communityName;
    private Map<String, Integer> cumulativeDischarge;
    private Map<String, Integer> cumulativePlay;
    private String pointNum;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Map<String, Integer> getCumulativeDischarge() {
        return cumulativeDischarge;
    }

    public void setCumulativeDischarge(Map<String, Integer> cumulativeDischarge) {
        this.cumulativeDischarge = cumulativeDischarge;
    }

    public Map<String, Integer> getCumulativePlay() {
        return cumulativePlay;
    }

    public void setCumulativePlay(Map<String, Integer> cumulativePlay) {
        this.cumulativePlay = cumulativePlay;
    }

    public String getPointNum() {
        return pointNum;
    }

    public void setPointNum(String pointNum) {
        this.pointNum = pointNum;
    }
}
