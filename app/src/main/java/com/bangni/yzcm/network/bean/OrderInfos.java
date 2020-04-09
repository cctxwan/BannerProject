package com.bangni.yzcm.network.bean;

import java.util.List;

/**
 * 订单
 */
public class OrderInfos {
    /**
     * isFirstPage : true
     * isLastPage : false
     * list : [{"adName":"过年_a_25-55男","cumulativeNumber":0,"cumulativePlay":52886,"endTime":1588217689000,"pid":102,"putNum":0,"startTime":1584948071000,"status":1},{"adName":"爱心传递_25-55_女","cumulativeNumber":3221,"cumulativePlay":53943,"endTime":1585366712000,"pid":103,"putNum":5,"startTime":1585120871000,"status":3},{"adName":"扫黑除恶_55_女","cumulativeNumber":6675,"cumulativePlay":51440,"endTime":null,"pid":104,"putNum":1,"startTime":1585120871000,"status":2},{"adName":"乘梯_25-55_女","cumulativeNumber":3221,"cumulativePlay":53787,"endTime":null,"pid":105,"putNum":4,"startTime":1585120871000,"status":2},{"adName":"过年_b1","cumulativeNumber":13117,"cumulativePlay":479623,"endTime":null,"pid":106,"putNum":9,"startTime":null,"status":2},{"adName":"过年_c","cumulativeNumber":0,"cumulativePlay":0,"endTime":null,"pid":107,"putNum":0,"startTime":null,"status":1},{"adName":"11","cumulativeNumber":0,"cumulativePlay":0,"endTime":null,"pid":122,"putNum":0,"startTime":null,"status":1},{"adName":"12-25女性","cumulativeNumber":0,"cumulativePlay":10090,"endTime":null,"pid":123,"putNum":0,"startTime":null,"status":1},{"adName":"黑曜堂植物染发(景、家、裕)","cumulativeNumber":0,"cumulativePlay":30863,"endTime":null,"pid":124,"putNum":0,"startTime":null,"status":1},{"adName":"女性修车12-25","cumulativeNumber":0,"cumulativePlay":0,"endTime":null,"pid":126,"putNum":0,"startTime":null,"status":1}]
     * pageNum : 1
     * pageSize : 10
     * pages : 2
     * total : 11
     */

    private boolean isFirstPage;
    private boolean isLastPage;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
    private List<ListBean> list;

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * adName : 过年_a_25-55男
         * cumulativeNumber : 0
         * cumulativePlay : 52886
         * endTime : 1588217689000
         * pid : 102
         * putNum : 0
         * startTime : 1584948071000
         * status : 1
         */

        private String adName;
        private int cumulativeNumber;
        private int cumulativePlay;
        private long endTime;
        private int pid;
        private int putNum;
        private long startTime;
        private int status;

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public int getCumulativeNumber() {
            return cumulativeNumber;
        }

        public void setCumulativeNumber(int cumulativeNumber) {
            this.cumulativeNumber = cumulativeNumber;
        }

        public int getCumulativePlay() {
            return cumulativePlay;
        }

        public void setCumulativePlay(int cumulativePlay) {
            this.cumulativePlay = cumulativePlay;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getPutNum() {
            return putNum;
        }

        public void setPutNum(int putNum) {
            this.putNum = putNum;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
