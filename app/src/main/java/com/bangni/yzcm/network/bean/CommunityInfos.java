package com.bangni.yzcm.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 所有社区列表
 */
public class CommunityInfos implements Parcelable {

        /**
         * communityName : 正式环境测试小区
         * pid : 12
         */

        private String communityName;
        private int pid;

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.pid);
            dest.writeString(this.communityName);
        }

        public CommunityInfos() {
        }

        protected CommunityInfos(Parcel in) {
            this.pid = in.readInt();
            this.communityName = in.readString();
        }

        public static final Creator<CommunityInfos> CREATOR = new Creator<CommunityInfos>() {
            @Override
            public CommunityInfos createFromParcel(Parcel source) {
                return new CommunityInfos(source);
            }

            @Override
            public CommunityInfos[] newArray(int size) {
                return new CommunityInfos[size];
            }
        };


}
