package com.spider.billboard.bean;

import java.util.Date;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public class Billboard {

    private long id;
    private Date time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
