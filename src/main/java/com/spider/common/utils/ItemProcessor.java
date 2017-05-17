package com.spider.common.utils;

import java.util.List;

/**
 * Created by xiang.gao on 2017/5/17.
 * project majorSpider
 */
public interface ItemProcessor<T> {

    void doInProcess(List<T> list);
}
