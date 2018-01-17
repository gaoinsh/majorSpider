package com.fanli.holder;


import com.fanli.bean.WebSpiderRequest;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiang.gao on 2018/1/17.
 * project 51fanli
 * 通过set去重
 *
 */
public class DuplicateUrlRemover {

    //去重后的所有链接
    private Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    /**
     * @param request 抓取到的url
     * @return true if duplicated
     */
    public boolean isDuplicated(WebSpiderRequest request) {
        return !urls.add(request.getUrl());
    }

    public int getSize() {
        return urls.size();
    }

}
