package com.spider.common.utils;

/**
 * Created by xiang.gao on 2017/5/18.
 * project majorSpider
 */
public interface DataProcessorService<T> {

    void doInProcess(T o);
}
