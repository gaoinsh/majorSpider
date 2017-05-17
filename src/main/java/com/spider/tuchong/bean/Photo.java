package com.spider.tuchong.bean;

/**
 * Created by xiang.gao on 2017/5/16.
 * project majorSpider
 */
public class Photo {

    private Long id;
    private Long sourceId;
    private Long picId;
    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "sourceId=" + sourceId +
                ", picId=" + picId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
