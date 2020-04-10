package com.bangni.yzcm.network.bean;

import java.util.List;

public class OrderBannerModel {
    /**
     * isFirstPage : true
     * isLastPage : true
     * list : [{"createDate":1586484637000,"pid":6,"targetParam":"","targetUrl":"http://www.baidu.com","updateDate":1586484637000,"url":"http://images.81dd.cn/6843099a0d124b869c41d993a5b81396"},{"createDate":1586484649000,"pid":7,"targetParam":"","targetUrl":"www.hao123.com","updateDate":1586485057000,"url":"http://images.81dd.cn/5b2f82a624594f9a8110e27122c96d71"}]
     * pageNum : 1
     * pageSize : 20
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
         * createDate : 1586484637000
         * pid : 6
         * targetParam :
         * targetUrl : http://www.baidu.com
         * updateDate : 1586484637000
         * url : http://images.81dd.cn/6843099a0d124b869c41d993a5b81396
         */

        private long createDate;
        private int pid;
        private String targetParam;
        private String targetUrl;
        private long updateDate;
        private String url;

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getTargetParam() {
            return targetParam;
        }

        public void setTargetParam(String targetParam) {
            this.targetParam = targetParam;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
