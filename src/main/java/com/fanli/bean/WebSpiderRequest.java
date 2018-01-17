package com.fanli.bean;

import java.util.Map;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 */
public class WebSpiderRequest {

    private long id;
    private String url;
    private Map<String, Object> extraData;

    public WebSpiderRequest() {
    }

    public WebSpiderRequest(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebSpiderRequest that = (WebSpiderRequest) o;

        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

}
