package com.bangni.yzcm.network.bean;

import com.google.gson.annotations.SerializedName;

public class StatisitcsInfos {
    /**
     * communityName : 辉少
     * cumulativeDischarge : {"12-31":633,"1-1":1744,"1-2":84}
     * cumulativePlay : null
     * pointNum : 0
     */

    private String communityName;
    private CumulativeDischargeBean cumulativeDischarge;
    private Object cumulativePlay;
    private String pointNum;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public CumulativeDischargeBean getCumulativeDischarge() {
        return cumulativeDischarge;
    }

    public void setCumulativeDischarge(CumulativeDischargeBean cumulativeDischarge) {
        this.cumulativeDischarge = cumulativeDischarge;
    }

    public Object getCumulativePlay() {
        return cumulativePlay;
    }

    public void setCumulativePlay(Object cumulativePlay) {
        this.cumulativePlay = cumulativePlay;
    }

    public String getPointNum() {
        return pointNum;
    }

    public void setPointNum(String pointNum) {
        this.pointNum = pointNum;
    }

    public static class CumulativeDischargeBean {
        /**
         * 12-31 : 633
         * 1-1 : 1744
         * 1-2 : 84
         */

        @SerializedName("12-31")
        private int _$1231;
        @SerializedName("1-1")
        private int _$11;
        @SerializedName("1-2")
        private int _$12;

        public int get_$1231() {
            return _$1231;
        }

        public void set_$1231(int _$1231) {
            this._$1231 = _$1231;
        }

        public int get_$11() {
            return _$11;
        }

        public void set_$11(int _$11) {
            this._$11 = _$11;
        }

        public int get_$12() {
            return _$12;
        }

        public void set_$12(int _$12) {
            this._$12 = _$12;
        }
    }
}
