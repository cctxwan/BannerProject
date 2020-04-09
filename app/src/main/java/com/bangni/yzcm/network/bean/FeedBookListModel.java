package com.bangni.yzcm.network.bean;

import java.util.List;

/**
 * 查看反馈列表
 */
public class FeedBookListModel {

    /**
     * isFirstPage : true
     * isLastPage : true
     * list : [{"feedbackContent":"我是意见反馈","feedbackTime":1585729230000,"replyContent":"1","replyTime":1585807989000},{"feedbackContent":"sdasdasd11111","feedbackTime":1585809286000,"replyContent":"我处理了意见反馈，balabala","replyTime":1585812136000}]
     * pageNum : 1
     * pageSize : 10
     * pages : 1
     * total : 2
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
         * feedbackContent : 我是意见反馈
         * feedbackTime : 1585729230000
         * replyContent : 1
         * replyTime : 1585807989000
         */

        private String feedbackContent;
        private long feedbackTime;
        private String replyContent;
        private long replyTime;

        public String getFeedbackContent() {
            return feedbackContent;
        }

        public void setFeedbackContent(String feedbackContent) {
            this.feedbackContent = feedbackContent;
        }

        public long getFeedbackTime() {
            return feedbackTime;
        }

        public void setFeedbackTime(long feedbackTime) {
            this.feedbackTime = feedbackTime;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public long getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(long replyTime) {
            this.replyTime = replyTime;
        }
    }
}
