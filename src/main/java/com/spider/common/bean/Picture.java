package com.spider.common.bean;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class Picture {

    private Long id;
    private String url;
    private String localUrl;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
